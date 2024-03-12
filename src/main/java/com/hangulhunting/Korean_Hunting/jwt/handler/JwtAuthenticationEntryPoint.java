package com.hangulhunting.Korean_Hunting.jwt.handler;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.hangulhunting.Korean_Hunting.dto.TokenETC;
import com.hangulhunting.Korean_Hunting.jwt.TokenProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	private final TokenProvider tokenProvider;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		if (request.getHeader(TokenETC.AUTHORIZATION) != null) {
	        // 토큰을 사용하여 인증을 시도합니다.
	        String token = request.getHeader(TokenETC.AUTHORIZATION).substring(7); // 헤더에서 토큰 추출
	        Authentication authentication = tokenProvider.getAuthentication(token); // 토큰을 사용하여 인증 객체 생성
	        if (authentication != null && authentication.isAuthenticated()) {
	            // 토큰 기반으로 인증된 사용자입니다.
	            log.info("인증 완료");
	            log.info("1. {}", authentication.getName());
	            log.info("2. {}", authentication.getAuthorities());
	            log.info("3. {}", tokenProvider.validateToken(token));
	            log.info("4, {}", authException);
	        } else {
	            // 인증되지 않은 사용자입니다.
	            log.info("인증 실패");
	        }
	        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	    }
	}

}
