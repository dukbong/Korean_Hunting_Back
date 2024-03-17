package com.hangulhunting.Korean_Hunting.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
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
import com.hangulhunting.Korean_Hunting.service.BlackListService;
import com.hangulhunting.Korean_Hunting.service.RefreshTokenService;
import com.hangulhunting.Korean_Hunting.service.UserService;

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
	
	// 토큰 생성
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
	
	// 인증 권한 정보 가져오기
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
	
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
    
    // 유효성 검사
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
    
    // 만료 10분전인 토큰으로 요청시 새로운 토큰 발급 ( 중단 되지 않도록 하기 위함 )
    public boolean reissuanceTimeCheck(String token) {
    	Claims claims = parseClaims(token);
    	Date expirationDate = claims.getExpiration();
    	Date now = new Date();
    	long maxreissuanceTime = 10 * 60 * 1000; // 20분
    	return expirationDate.getTime() - now.getTime() > maxreissuanceTime;
    }

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
