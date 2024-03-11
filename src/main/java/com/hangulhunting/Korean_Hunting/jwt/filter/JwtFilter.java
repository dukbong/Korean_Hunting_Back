package com.hangulhunting.Korean_Hunting.jwt.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hangulhunting.Korean_Hunting.jwt.TokenProvider;
import com.hangulhunting.Korean_Hunting.repository.BlackListRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private final TokenProvider tokenProvider;
    private final BlackListRepository blackListRepository;
    
    private String resolveToken(HttpServletRequest request) {
    	String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    	log.info("bearer Token = {}", bearerToken);
    	if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
    		return bearerToken.substring(7);
    	}
    	return null;
    }
    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = resolveToken(request);
		if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
			if(!blackListRepository.existsByToken(jwt)) {
				Authentication authentication = tokenProvider.getAuthentication(jwt);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		
		filterChain.doFilter(request, response);
	}
    
}
