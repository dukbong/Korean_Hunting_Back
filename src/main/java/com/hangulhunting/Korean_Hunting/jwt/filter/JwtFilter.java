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
import com.hangulhunting.Korean_Hunting.serviceImpl.BlackListService;
import com.hangulhunting.Korean_Hunting.serviceImpl.RefreshTokenService;

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

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwt = resolveToken(request);
		if (StringUtils.hasText(jwt)) {
			if (tokenProvider.validateToken(jwt) && tokenProvider.reissuanceTimeCheck(jwt)) {
				handleValidToken(jwt);
			} else {
				handleExpiredToken(jwt, response);
			}
		}
		filterChain.doFilter(request, response);
	}
	
    /***
     * HTTP 요청에서 JWT 토큰을 추출하는 메서드
     * 
     * @param request HTTP 요청 객체
     * @return 추출된 JWT 토큰
     */
	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(TokenETC.AUTHORIZATION);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TokenETC.PREFIX)) {
			return bearerToken.substring(7);
		}
		return null;
	}

	/***
	 * 주어진 JWT 토큰에 해당하는 사용자의 리프레시 토큰을 찾아 반환하는 메서드
	 * 
	 * @param token JWT 토큰
	 * @return 해당 사용자의 리프레시 토큰 값, 존재하지 않을 경우 null을 반환합니다.
	 */
	private String resolveRefreshToken(String token) {
		String username = tokenProvider.getAuthentication(token).getName();
		Optional<RefreshToken> refreshToken = refreshTokenService.findByUserId(username);
		if (refreshToken.isPresent()) {
			return refreshToken.get().getValue();
		}
		return null;
	}
	
    /***
     * 만료된 토큰을 처리하는 메서드, 새로운 토큰을 발급하고 처리
     * 
     * @param jwt 만료된 JWT 토큰
     * @param response HTTP 응답 객체
     */
	private void handleExpiredToken(String jwt, HttpServletResponse response) {
        String refreshToken = resolveRefreshToken(jwt);
        if (refreshToken != null && tokenProvider.validateToken(refreshToken)) {
            TokenDto newTokenDto = tokenProvider.refreshGenerateTokenDto(refreshToken);
            if (newTokenDto != null) {
                setSecurityContext(newTokenDto.getAccessToken());
                blackListService.save(jwt);
                response.setHeader(TokenETC.AUTHORIZATION, TokenETC.PREFIX + newTokenDto.getAccessToken());
            }
        }
    }
	
    /***
     * 유효한 토큰을 처리하는 메서드
     * 
     * @param jwt 유효한 JWT 토큰
     */
    private void handleValidToken(String jwt) {
        if (!blackListService.existsByToken(jwt)) {
            setSecurityContext(jwt);
        }
    }
	
    /***
     * 보안 컨텍스트를 설정하는 메서드
     * 
     * @param token JWT 토큰
     */
	private void setSecurityContext(String token) {
		Authentication authentication = tokenProvider.getAuthentication(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
