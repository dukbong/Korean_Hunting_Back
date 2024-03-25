package com.hangulhunting.Korean_Hunting.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.hangulhunting.Korean_Hunting.dto.token.TokenDto;
import com.hangulhunting.Korean_Hunting.entity.RefreshToken;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;
import com.hangulhunting.Korean_Hunting.jwt.etc.TokenETC;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;
import com.hangulhunting.Korean_Hunting.serviceImpl.BlackListService;
import com.hangulhunting.Korean_Hunting.serviceImpl.RefreshTokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@PropertySource(value = {"jwt.properties"})
public class TokenProvider {

	private final Key key;
	private final BlackListService blackListService;
	private final RefreshTokenService refreshTokenService;
	
	public TokenProvider(@Value("${jwt.secret}") String secretKey, BlackListService blackListService, RefreshTokenService refreshTokenService) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        
        this.blackListService = blackListService;
        this.refreshTokenService = refreshTokenService;
    }
	
    /**
     * 인증 정보를 기반으로 JWT 토큰을 생성하여 반환하는 메소드
     * 
     * @param authentication 인증 정보
     * @return 생성된 토큰 정보가 담긴 객체
     */
	public TokenDto generateTokenDto(Authentication authentication) {
		String authorities = authentication.getAuthorities().stream()
										   .map(GrantedAuthority::getAuthority)
										   .collect(Collectors.joining(","));
		long now = (new Date()).getTime();
		
		Date tokenExpiresIn = new Date(now + TokenETC.ACCESS_TOKEN_EXPIRE_TIME);
		String accessToken = Jwts.builder()
								 .setSubject(authentication.getName())
								 .claim(TokenETC.AUTHORITIES_KEY, authorities)
								 .setExpiration(tokenExpiresIn)
								 .signWith(key, SignatureAlgorithm.HS512)
								 .compact();

		String refreshToken = Jwts.builder()
								  .setExpiration(new Date(now + TokenETC.REFRESH_TOKEN_EXPIRE_TIME))
								  .signWith(key, SignatureAlgorithm.HS512)
								  .compact();
		
		return TokenDto.builder()
					   .grantType(TokenETC.BEARER_TYPE)
					   .accessToken(accessToken)
					   .tokenExpiresIn(tokenExpiresIn.getTime())
					   .refreshToken(refreshToken)
					   .build();
	}
	
	public String apigenerateToken(Authentication authentication) {
		long now = (new Date()).getTime();
		Date tokenExpiresIn = new Date(now + TokenETC.API_TOKEN_EXPIRE_TIME);
		return Jwts.builder()
				   .setSubject(authentication.getName())
				   .claim(TokenETC.AUTHORITIES_KEY, "API")
				   .setExpiration(tokenExpiresIn)
				   .signWith(key, SignatureAlgorithm.HS512)
				   .compact();
	}
	
	
	
    /**
     * JWT 토큰을 파싱하여 인증 객체를 반환하는 메소드
     * 
     * @param accessToken 파싱할 JWT 토큰
     * @return 인증 객체
     */
	public Authentication getAuthentication(String accessToken) {
		Claims claims = parseClaims(accessToken);
		
		if(claims.get(TokenETC.AUTHORITIES_KEY) == null) {
			throw new CustomException(ErrorCode.NO_AUTHORITY);
		}
		
		Collection<? extends GrantedAuthority> authorities = 
				Arrays.stream(claims.get(TokenETC.AUTHORITIES_KEY).toString().split(","))
				      .map(SimpleGrantedAuthority::new)
				      .collect(Collectors.toList());
		
		UserDetails principal = new User(claims.getSubject(), "", authorities);
		
		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}
	
    /**
     * 주어진 JWT 토큰에서 클레임을 파싱하는 메소드
     * 
     * @param accessToken 파싱할 JWT 토큰
     * @return 파싱된 클레임
     */
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
    
    /**
     * JWT 토큰의 유효성을 검사하는 메소드
     * 
     * @param token 검사할 JWT 토큰
     * @return 유효성 여부
     */
    public boolean validateToken(String token) {
    	try {
    		Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    		return true;
    	} catch (SecurityException | MalformedJwtException e) {
    		log.error("{}", "잘못된 JWT 서명입니다.");
    	} catch (ExpiredJwtException e) {
    		log.error("{}", "만료된 JWT 토큰입니다.");
    		if(parseClaims(token).getSubject() == null) {
    			refreshTokenService.deleteByValue(token);
    		} else {
    			blackListService.save(token);
    		}
    	} catch (UnsupportedJwtException e) {
    		log.error("{}", "지원되지 않는 JWT 토큰입니다.");
    	} catch (IllegalArgumentException e) {
    		log.error("JWT 토큰이 잘못되었습니다.");
    	}
    	return false;
    }
    
    /**
     * 토큰의 만료 시간이 만료 20분 전인지 확인하는 메소드
     * 
     * @param token 확인할 토큰
     * @return 토큰의 만료 시간이 재발급 시간 이전이면 true, 그렇지 않으면 false를 반환합니다.
     */
    public boolean reissuanceTimeCheck(String token) {
    	Claims claims = parseClaims(token);
    	Date expirationDate = claims.getExpiration();
    	Date now = new Date();
    	long maxreissuanceTime = 10 * 60 * 1000; // 20분
    	return expirationDate.getTime() - now.getTime() > maxreissuanceTime;
    }

    /**
     * 리프레시 토큰을 이용하여 새로운 엑세스 토큰을 발급하는 메소드
     * 
     * @param refreshToken 새로 발급할 토큰의 리프레시 토큰
     * @return 새로 발급된 엑세스 토큰 정보를 포함한 토큰 DTO 객체를 반환합니다.
     */
    @Transactional
	public TokenDto refreshGenerateTokenDto(String refreshToken) {
		Optional<RefreshToken>tokenInfo = refreshTokenService.findByValue(refreshToken);
		if(tokenInfo.isPresent()) {
			UserEntity userEntity = tokenInfo.get().getUserEntity();
			long now = (new Date()).getTime();
			Date tokenExpiresIn = new Date(now + TokenETC.ACCESS_TOKEN_EXPIRE_TIME);
			String accessToken = Jwts.builder()
									 .setSubject(userEntity.getUserId())
									 .claim(TokenETC.AUTHORITIES_KEY, userEntity.getRole())
									 .setExpiration(tokenExpiresIn)
									 .signWith(key, SignatureAlgorithm.HS512)
									 .compact();
			
			return TokenDto.builder()
						   .accessToken(accessToken)
						   .refreshToken(refreshToken)
						   .tokenExpiresIn(tokenExpiresIn.getTime())
						   .build();
		}
		return null;
	}


}
