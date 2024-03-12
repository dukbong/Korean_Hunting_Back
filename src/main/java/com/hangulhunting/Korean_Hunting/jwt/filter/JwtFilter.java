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
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.jwt.TokenProvider;
import com.hangulhunting.Korean_Hunting.service.BlackListService;

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
	private final BlackListService blackListService;

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(TokenETC.AUTHORIZATION);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TokenETC.PREFIX)) {
			return bearerToken.substring(7);
		}
		return null;
	}

	private String resolveRefreshToken(String token) {
		String username = tokenProvider.getAuthentication(token).getName();
		Optional<UserEntity> userEntity = tokenProvider.findUserInfo(username);
		if (userEntity.isPresent()) {
			return userEntity.get().getRefreshToken().getValue();
		}
		return null;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwt = resolveToken(request);
		if (StringUtils.hasText(jwt)) {
			log.info("tokenProvider.validateToken(jwt)       = {}", tokenProvider.validateToken(jwt));
			log.info("tokenProvider.reissuanceTimeCheck(jwt) = {}", tokenProvider.reissuanceTimeCheck(jwt));
			if (tokenProvider.validateToken(jwt) && tokenProvider.reissuanceTimeCheck(jwt)) {
				if (!blackListService.existsByToken(jwt)) {
					setSecurityContext(jwt);
				}
			} else {
				String refreshToken = resolveRefreshToken(jwt);
				if (refreshToken != null) {
					if (tokenProvider.validateToken(refreshToken)) {
						TokenDto newTokenDto = tokenProvider.refreshGenerateTokenDto(refreshToken);
						if (newTokenDto != null) {
							setSecurityContext(newTokenDto.getAccessToken());
							log.info("새로운 토큰 발급 : {}", newTokenDto.getAccessToken());
							response.setHeader(TokenETC.AUTHORIZATION, TokenETC.PREFIX + newTokenDto.getAccessToken());
						}
					}
				}
			}
		}

		filterChain.doFilter(request, response);
	}
	
	private void setSecurityContext(String token) {
		Authentication authentication = tokenProvider.getAuthentication(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
