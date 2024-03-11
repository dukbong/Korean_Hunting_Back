package com.hangulhunting.Korean_Hunting.jwt.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hangulhunting.Korean_Hunting.dto.TokenDto;
import com.hangulhunting.Korean_Hunting.dto.TokenETC;
import com.hangulhunting.Korean_Hunting.entity.RefreshToken;
import com.hangulhunting.Korean_Hunting.jwt.TokenProvider;
import com.hangulhunting.Korean_Hunting.repository.BlackListRepository;
import com.hangulhunting.Korean_Hunting.repository.RefreshTokenRepository;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;

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
	
    private final TokenProvider tokenProvider;
    private final BlackListRepository blackListRepository;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    
    private String resolveToken(HttpServletRequest request) {
    	String bearerToken = request.getHeader(TokenETC.AUTHORIZATION);
    	if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(TokenETC.PREFIX)) {
    		return bearerToken.substring(7);
    	}
    	return null;
    }
    
	private String resolveRefreshToken(HttpServletRequest request) {
		String token = resolveToken(request);
		String username = tokenProvider.getAuthentication(token).getName();
		Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserEntityId(userRepository.findByUserId(username).get().getId());
		if(refreshToken.isPresent()) {
			return refreshToken.get().getValue();
		}
		return null;
	}
    
    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		log.info("JWT Filter Start...");
		
		String jwt = resolveToken(request);
		log.info("Access Token ....");
		if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
			if(!blackListRepository.existsByToken(jwt)) {
				Authentication authentication = tokenProvider.getAuthentication(jwt);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		
	    if (jwt != null && !tokenProvider.validateToken(jwt)) {
	    	log.info("Refresh Token ....");
	        String refreshToken = resolveRefreshToken(request);
	        if(refreshToken != null) {
	        	if (tokenProvider.validateToken(refreshToken)) {
	        		// 새로운 accessToken 발급
	        		Authentication authentication = tokenProvider.getAuthentication(refreshToken);
	        		TokenDto newTokenDto = tokenProvider.generateTokenDto(authentication);
	        		// 클라이언트에게 새로운 accessToken을 반환할 수 있도록 설정
	        		response.setHeader(TokenETC.AUTHORIZATION, TokenETC.PREFIX + newTokenDto.getAccessToken());
	        	} else {
	        		refreshTokenRepository.deleteByValue(refreshToken);
	        	}
	        }
	    }
		
		filterChain.doFilter(request, response);
	}
    
}
