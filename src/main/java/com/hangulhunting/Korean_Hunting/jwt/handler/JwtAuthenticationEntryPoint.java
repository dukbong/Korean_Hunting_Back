package com.hangulhunting.Korean_Hunting.jwt.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		log.info("인증 문제");
		log.info("token = {}", request.getHeader("Authorization"));
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write("인증 문제가 발생하였습니다. 다시 로그인 해주세요.");
	}
}
