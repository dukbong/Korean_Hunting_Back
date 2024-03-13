package com.hangulhunting.Korean_Hunting.service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.dto.response.UserResDto;
import com.hangulhunting.Korean_Hunting.dto.token.TokenDto;
import com.hangulhunting.Korean_Hunting.entity.RefreshToken;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.entity.enumpackage.UserRole;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;
import com.hangulhunting.Korean_Hunting.jwt.TokenProvider;
import com.hangulhunting.Korean_Hunting.jwt.etc.TokenETC;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final RefreshTokenService refreshTokenService;
	private final BlackListService blackListService;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final TokenProvider tokenProvider;
	

	public boolean existsAnyNullOrBlank(String str) {
		if (str == null || str.isEmpty()) {
			return true;
		}
		return false;
	}

	public boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		return !matcher.matches();
	}

	public UserResDto joinProcess(User user) {
		if (userRepository.existsByUserId(user.getUserId()))
			throw new CustomException(ErrorCode.NAME_ALREADY_EXISTS, user.getUserId());

		if (existsAnyNullOrBlank(user.getUserId()))
			throw new CustomException(ErrorCode.MEMBER_IDS_IS_EMPTY_OR_NULL, user.getUserId());

		if (existsAnyNullOrBlank(user.getUserPwd()))
			throw new CustomException(ErrorCode.MEMBER_PWD_IS_EMPTY_OR_NULL, user.getUserPwd());

		if (isValidEmail(user.getEmail()))
			throw new CustomException(ErrorCode.INVALID_EMAIL, user.getEmail());

		UserEntity userEntity = UserEntity.builder().userId(user.getUserId())
				.userPwd(bCryptPasswordEncoder.encode(user.getUserPwd())).email(user.getEmail())
				.company(user.getCompany()).role(UserRole.ROLE_USER).build();

		userRepository.save(userEntity);

		return new UserResDto("회원가입에 성공하였습니다.");
	}

	public TokenDto loginProcess(User user) {
		// 1. id / pw 기반으로 authenticationToken 생성
		UsernamePasswordAuthenticationToken authenticationToken = user.toAuthentication();
		// 2. 실제 검증 (비밀번호 체크)
		// authenticate 메소드가 실행시 CustomUserDetailsService에서 만들었던 loadUserByUsername 실행
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		// 3. 인증 정보를 기반으로 jwt 생성
		TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
		// 4. refresh 토큰 저장
		UserEntity loginUser = userRepository.findByUserId(authentication.getName()).get();
		RefreshToken refreshToken = RefreshToken.builder()
				 								.value(tokenDto.getRefreshToken())
				 								.userEntity(loginUser)
				 								.build();
		refreshTokenService.save(refreshToken);
		return tokenDto;
	}

	public User userInfo() {
		Authentication  authentication = SecurityContextHolder.getContext().getAuthentication();
		String userId = authentication.getName();
		UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND_INFO));
		User user = User.builder().userId(userEntity.getUserId()).email(userEntity.getEmail()).company(userEntity.getCompany()).build();
		return user;
	}

	@Transactional
	public void logoutProcess(HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String token = request.getHeader(TokenETC.AUTHORIZATION);
		log.info("logout user token  = {}", token);
		blackListService.save(token.substring(7));
		
		// 로그아웃시 refresh Token 삭제
		String username = authentication.getName();
		Optional<UserEntity> userEntity = userRepository.findByUserId(username);
		if(userEntity.isPresent()) {
			refreshTokenService.deleteByValue(userEntity.get().getRefreshToken().getValue());
		}
	}

}
