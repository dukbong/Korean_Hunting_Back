package com.hangulhunting.Korean_Hunting.serviceImpl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.dto.response.UserResDto;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.entity.enumpackage.Regex;
import com.hangulhunting.Korean_Hunting.entity.enumpackage.UserRole;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterUserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserResDto registerUser(User user) {

		validateUserId(user.getUserId());
		
		checkJoinRoute(user);
		
		validateEmail(user.getEmail());

		userRepository.save(convertDtoToEntity(user));

		return new UserResDto("회원가입을 축하드립니다.");
	}
	
	private void checkJoinRoute(User user) {
		boolean check = user.getJoinRoute() == null || user.getJoinRoute().isEmpty();
		if(check) {
			validateUserPwd(user.getUserPwd());
		}
	}

	private UserEntity convertDtoToEntity(User user) {
		return UserEntity.builder().userId(user.getUserId())
								   .userPwd(bCryptPasswordEncoder.encode(user.getUserPwd()))
								   .company(user.getCompany())
								   .email(user.getEmail())
								   .joinRoute(user.getJoinRoute())
								   .role(UserRole.ROLE_USER).build();
	}

	private void validateUserId(String userId) {
		if (userId == null || userId.isEmpty()) {
			throw new CustomException(ErrorCode.USER_ID_REQUIRED);
		}

		boolean result = userRepository.existsByUserId(userId);
		if (result) {
			throw new CustomException(ErrorCode.DUPLICATE_ID);
		}
	}

	private void validateUserPwd(String pwd) {
		if (pwd == null || pwd.isEmpty()) {
			throw new CustomException(ErrorCode.USER_PWD_REQUIRED);
		}

		if (pwd.length() < 8) {
			throw new CustomException(ErrorCode.PASSWORD_LENGTH);
		}

		if (!pwd.matches(Regex.UPPERCASE.getRegex())) {
			throw new CustomException(ErrorCode.PASSWORD_UPPERCASE);
		}

		if (!pwd.matches(Regex.LOWERCASE.getRegex())) {
			throw new CustomException(ErrorCode.PASSWORD_LOWERCASE);
		}

		if (!pwd.matches(Regex.SPECIAL_CHARACTER.getRegex())) {
			throw new CustomException(ErrorCode.PASSWORD_SPECIAL_CHARACTER);
		}
	}

	private void validateEmail(String email) {
		if (email == null || email.isEmpty()) {
			throw new CustomException(ErrorCode.USER_EMAIL_REQUIRED);
		}

		Pattern pattern = Pattern.compile(Regex.EMAIL_REGEX.getRegex());
		Matcher matcher = pattern.matcher(email);

		if (!matcher.matches()) {
			throw new CustomException(ErrorCode.EMAIL_FORMAT);
		}
	}
}
