package com.hangulhunting.Korean_Hunting.jwt.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hangulhunting.Korean_Hunting.dto.token.TokenDto;
import com.hangulhunting.Korean_Hunting.entity.RefreshToken;
import com.hangulhunting.Korean_Hunting.jwt.TokenProvider;
import com.hangulhunting.Korean_Hunting.jwt.etc.TokenETC;
import com.hangulhunting.Korean_Hunting.service.BlackListService;
import com.hangulhunting.Korean_Hunting.service.RefreshTokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final TokenProvider tokenProvider;
	private final BlackListService blackListService;
	private final RefreshTokenService refreshTokenService;

	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(TokenETC.AUTHORIZATION);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TokenETC.PREFIX)) {
			return bearerToken.substring(7);
		}
		return null;
	}

	private String resolveRefreshToken(String token) {
		String username = tokenProvider.getAuthentication(token).getName();
		Optional<RefreshToken> refreshToken = refreshTokenService.findByUserId(username);
		if (refreshToken.isPresent()) {
			return refreshToken.get().getValue();
		}
		return null;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwt = resolveToken(request);
		if (StringUtils.hasText(jwt)) {
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
							blackListService.save(jwt);
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
