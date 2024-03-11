package com.hangulhunting.Korean_Hunting.jwt.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.hangulhunting.Korean_Hunting.dto.TokenETC;

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
		log.error("JwtAuthenticationEntryPoint!!!!!!");
		log.error("token = {}", request.getHeader(TokenETC.AUTHORIZATION));
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}

}
