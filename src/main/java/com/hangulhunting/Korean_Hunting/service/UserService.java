package com.hangulhunting.Korean_Hunting.service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hangulhunting.Korean_Hunting.dto.TokenDto;
import com.hangulhunting.Korean_Hunting.dto.TokenETC;
import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.dto.UserResDto;
import com.hangulhunting.Korean_Hunting.dto.UserRole;
import com.hangulhunting.Korean_Hunting.entity.BlackList;
import com.hangulhunting.Korean_Hunting.entity.RefreshToken;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;
import com.hangulhunting.Korean_Hunting.jwt.TokenProvider;
import com.hangulhunting.Korean_Hunting.repository.BlackListRepository;
import com.hangulhunting.Korean_Hunting.repository.RefreshTokenRepository;
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
	private final RefreshTokenRepository refreshTokenRepository;
	private final BlackListRepository blackListRepository;
	/* private final AuthenticationManager authenticationManager; */
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
		// authenticate 멧드가 실행시 CustomUserDetailsService에서 만들었던 loadUserByUsername 실행
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		// 3. 인증 정보를 기반으로 jwt 생성
		TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
		// 4. refresh 토큰 저장
		UserEntity loginUser = userRepository.findByUserId(authentication.getName()).get();
		RefreshToken refreshToken = RefreshToken.builder()
				 								.value(tokenDto.getRefreshToken())
				 								.userEntity(loginUser)
				 								.build();
		refreshTokenRepository.save(refreshToken);
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
		BlackList blackList = BlackList.builder().token(token.substring(7)).build();
		blackListRepository.save(blackList);
		
		// 로그아웃시 refresh Token 삭제
		String username = authentication.getName();
		Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserEntityId(userRepository.findByUserId(username).get().getId());
		if(refreshToken.isPresent()) {
			refreshTokenRepository.deleteByValue(refreshToken.get().getValue());
		}
	}
}
