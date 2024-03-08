package com.hangulhunting.Korean_Hunting.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		log.info("Login Porcess 진행중 .. ");
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		// 3. 인증 정보를 기반으로 jwt 생성
		TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
		// 4. refresh 토큰 저장
		RefreshToken refreshToken = RefreshToken.builder()
				                                .key(authentication.getName())
				 								.value(tokenDto.getRefreshToken())
				 								.build();
		 
		refreshTokenRepository.save(refreshToken);
		return tokenDto;
	}

	/*
	 * public TokenDto reissue(TokenRequestDto tokenRequestDto) { // 1. Refresh
	 * Token 검증 if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken()))
	 * { throw new RuntimeException("Refresh Token 이 유효하지 않습니다."); }
	 * 
	 * // 2. Access Token 에서 Member ID 가져오기 Authentication authentication =
	 * tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());
	 * 
	 * // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴 RefreshToken refreshToken =
	 * refreshTokenRepository.findByKey(authentication.getName()) .orElseThrow(() ->
	 * new RuntimeException("로그아웃 된 사용자입니다."));
	 * 
	 * // 4. Refresh Token 일치하는지 검사 if
	 * (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) { throw
	 * new RuntimeException("토큰의 유저 정보가 일치하지 않습니다."); }
	 * 
	 * // 5. 새로운 토큰 생성 TokenDto tokenDto =
	 * tokenProvider.generateTokenDto(authentication);
	 * 
	 * // 6. 저장소 정보 업데이트 RefreshToken newRefreshToken =
	 * refreshToken.updateValue(tokenDto.getRefreshToken());
	 * refreshTokenRepository.save(newRefreshToken);
	 * 
	 * // 토큰 발급 return tokenDto; }
	 */
	
	
	public User userInfo() {
		Authentication  authentication = SecurityContextHolder.getContext().getAuthentication();
		String userId = authentication.getName();
		UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND_INFO));
		User user = User.builder().userId(userEntity.getUserId()).email(userEntity.getEmail()).company(userEntity.getCompany()).build();
		return user;
	}

	public void logoutProcess(HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String token = request.getHeader(TokenETC.AUTHORIZATION);
//		String token = (String)authentication.getCredentials();
		log.info("logout user token  = {}", token);
		BlackList blackList = BlackList.builder().token(token.substring(7)).build();
		blackListRepository.save(blackList);
	}
}
