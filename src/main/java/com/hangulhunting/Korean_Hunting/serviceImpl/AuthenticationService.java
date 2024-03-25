package com.hangulhunting.Korean_Hunting.serviceImpl;

import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.dto.UserInfo;
import com.hangulhunting.Korean_Hunting.dto.token.TokenDto;
import com.hangulhunting.Korean_Hunting.entity.RefreshToken;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;
import com.hangulhunting.Korean_Hunting.jwt.TokenProvider;
import com.hangulhunting.Korean_Hunting.jwt.etc.TokenETC;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final RefreshTokenService refreshTokenService;
	private final BlackListService blackListService;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final TokenProvider tokenProvider;
	private final UserRepository userRepository;

	/**
	 * 사용자 로그인을 처리하는 서비스
	 * 
	 * @param user 로그인할 사용자 정보
	 * @return 로그인 결과와 JWT 토큰 정보
	 */
	public String loginProcess(User user) {
		Authentication authentication = authenticateUser(user);
		TokenDto tokenDto = generateToken(authentication);
		handleRefreshToken(authentication, tokenDto);
		return tokenDto.getAccessToken();	
	}

	/**
	 * RefreshToken을 처리하는 서비스
	 * 
	 * @param authentication 사용자 인증 정보
	 * @param tokenDto 토큰 DTO
	 */
	private void handleRefreshToken(Authentication authentication, TokenDto tokenDto) {
		UserEntity userEntity = findUserEntity(authentication);
	    Optional<RefreshToken> optionalRefreshToken = Optional.ofNullable(userEntity.getRefreshToken());
	    if (optionalRefreshToken.isPresent() && !tokenProvider.validateToken(optionalRefreshToken.get().getValue())) {
	   		refreshTokenService.deleteByValue(optionalRefreshToken.get().getValue());
	   		saveRefreshToken(tokenDto, userEntity);
	    }
	    optionalRefreshToken.orElseGet(() -> saveRefreshToken(tokenDto, userEntity));
	}
	
	/**
	 * 새로운 RefreshToken을 저장하는 메소드입니다.
	 * 
	 * @param tokenDto 토큰 DTO
	 * @param userEntity 사용자 엔터티
	 * @return 저장된 RefreshToken 객체
	 */
	private RefreshToken saveRefreshToken(TokenDto tokenDto, UserEntity userEntity) {
	    RefreshToken newRefreshToken = RefreshToken.builder()
										           .value(tokenDto.getRefreshToken())
										           .userEntity(userEntity)
										           .build();
	    refreshTokenService.save(newRefreshToken);
	    return newRefreshToken;
	}
	
	/**
	 * 사용자 엔터티를 찾는 메소드입니다.
	 * 
	 * @param authentication 사용자 인증 정보
	 * @return 찾은 사용자 엔터티
	 * @throws CustomException 사용자를 찾지 못했을 때 발생하는 예외
	 */
	private UserEntity findUserEntity(Authentication authentication) {
	    return userRepository.findByUserId(authentication.getName())
	            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND_INFO));
	}
	
	/**
	 * 사용자를 인증하는 메소드입니다.
	 * 
	 * @param user 사용자 정보
	 * @return 인증된 Authentication 객체
	 */
	public Authentication authenticateUser(User user) {
		// 1. id / pw 기반으로 authenticationToken 생성
		UsernamePasswordAuthenticationToken authenticationToken = user.toAuthentication();
		// 2. 실제 검증 (비밀번호 체크)
		// authenticate 메소드가 실행시 CustomUserDetailsService에서 만들었던 loadUserByUsername 실행
		return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
	}
	
	/**
	 * 토큰을 생성하는 메소드입니다.
	 * 
	 * @param authentication 사용자 인증 정보
	 * @return 생성된 TokenDto 객체
	 */
	private TokenDto generateToken(Authentication authentication) {
		return tokenProvider.generateTokenDto(authentication);
	}
	
	/**
	 * 현재 인증된 사용자의 정보를 가져오는 서비스
	 * 
	 * @return 현재 인증된 사용자 정보
	 * @throws CustomException 인증된 사용자 정보를 찾을 수 없을 때 발생하는 예외
	 */
	public UserInfo userInfo() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userId = authentication.getName();
		UserEntity userEntity = userRepository.findByUserId(userId)
				.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND_INFO));
		UserInfo userInfo = UserInfo.builder()
								.userId(userEntity.getUserId())
								.email(userEntity.getEmail())
								.company(userEntity.getCompany())
								.apiToken(userEntity.getApiTokenEntity().getApiToken())
								.build();
		return userInfo;
	}

	/**
	 * 사용자 로그아웃을 처리하는 서비스
	 * 
	 * @param request HTTP 요청 객체
	 */
	@Transactional
	public void logoutProcess(HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String token = request.getHeader(TokenETC.AUTHORIZATION);
		blackListService.save(token.substring(7));

		// 로그아웃시 refresh Token 삭제
		String username = authentication.getName();
		Optional<UserEntity> userEntity = userRepository.findByUserId(username);
		if (userEntity.isPresent()) {
			refreshTokenService.deleteByValue(userEntity.get().getRefreshToken().getValue());
		}
	}
}
