package com.hangulhunting.Korean_Hunting.service;

import java.util.AbstractMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hangulhunting.Korean_Hunting.dto.TokenDto;
import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.dto.UserResDto;
import com.hangulhunting.Korean_Hunting.dto.UserRole;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;
import com.hangulhunting.Korean_Hunting.jwt.TokenProvider;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	/* private final AuthenticationManager authenticationManager; */
	private final AuthenticationManagerBuilder managerBuilder;
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
		UsernamePasswordAuthenticationToken authenticationToken = user.toAuthentication();

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.generateTokenDto(authentication);
	}

	public Map<String, String> userInfo(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId)
											  .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND_BY_ID, userId));
		return Stream.of(
					        new AbstractMap.SimpleEntry<>("userId", userEntity.getUserId()),
					        new AbstractMap.SimpleEntry<>("email", userEntity.getEmail()),
					        new AbstractMap.SimpleEntry<>("company", userEntity.getCompany())
					    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}
}
