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

import com.hangulhunting.Korean_Hunting.dto.TokenDto;
import com.hangulhunting.Korean_Hunting.dto.TokenETC;
import com.hangulhunting.Korean_Hunting.entity.BlackList;
import com.hangulhunting.Korean_Hunting.entity.RefreshToken;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;
import com.hangulhunting.Korean_Hunting.repository.BlackListRepository;
import com.hangulhunting.Korean_Hunting.repository.RefreshTokenRepository;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenProvider {

	private final Key key;
	private final BlackListRepository blackListRepository;
	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;
	
	public TokenProvider(@Value("${jwt.secret}") String secretKey, BlackListRepository blackListRepository, UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        
        this.blackListRepository = blackListRepository;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }
	
	// 토큰 생성
	public TokenDto generateTokenDto(Authentication authentication) {
		String authorities = authentication.getAuthorities().stream()
										   .map(GrantedAuthority::getAuthority)
										   .collect(Collectors.joining(","));
		long now = (new Date()).getTime();
		
		Date tokenExpiresIn = new Date(now + TokenETC.ACCESS_TOKEN_EXPIRE_TIME);
		log.info("권한 -= {}", authorities);
		String accessToken = Jwts.builder()
								 .setSubject(authentication.getName())
								 .claim(TokenETC.AUTHORITIES_KEY, authorities)
								 .setExpiration(tokenExpiresIn)
								 .signWith(key, SignatureAlgorithm.HS512)
								 .compact();

		Optional<RefreshToken> checkToken = refreshTokenRepository.findByUserEntityId(userRepository.findByUserId(authentication.getName()).get().getId());
		String refreshToken = null;
		if(!checkToken.isPresent()) {
			// 로그인시 refreshToken이 없기 때문에 만들고
			// 재 발급의 경우 refreshToken을 만드는 일을 없앤다.
			refreshToken = Jwts.builder()
							   .setExpiration(new Date(now + TokenETC.REFRESH_TOKEN_EXPIRE_TIME))
							   .signWith(key, SignatureAlgorithm.HS512)
							   .compact();
		}
		
		
		return TokenDto.builder()
					   .grantType(TokenETC.BEARER_TYPE)
					   .accessToken(accessToken)
					   .tokenExpiresIn(tokenExpiresIn.getTime())
					   .refreshToken(refreshToken)
					   .build();
	}
	
	// 인증 정보 가져오기
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
    		// 만료된 토큰은 즉시 blackList에 넣는다.
    		BlackList blackList = BlackList.builder().token(token).build();
    		blackListRepository.save(blackList);
    	} catch (UnsupportedJwtException e) {
    		log.error("{}", "지원되지 않는 JWT 토큰입니다.");
    	} catch (IllegalArgumentException e) {
    		log.error("JWT 토큰이 잘못되었습니다.");
    	}
    	return false;
    }
}
