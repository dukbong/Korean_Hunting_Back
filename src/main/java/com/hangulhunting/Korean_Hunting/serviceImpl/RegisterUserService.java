package com.hangulhunting.Korean_Hunting.serviceImpl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.dto.response.UserResDto;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
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

	/**
	 * 사용자 등록을 처리하는 서비스
	 * 
	 * @param user 등록할 사용자 정보
	 * @return 사용자 등록 결과
	 */
	public UserResDto registerUser(User user) {
		validateUser(user);
		UserEntity userEntity = UserEntity.builder()
										  .userId(user.getUserId())
										  .userPwd(bCryptPasswordEncoder.encode(user.getUserPwd()))
										  .email(user.getEmail())
										  .company(user.getCompany())
										  .role(UserRole.ROLE_USER)
										  .build();
		userRepository.save(userEntity);
		return new UserResDto("회원가입에 성공하였습니다.");
	}

	/**
	 * 사용자 정보 유효성을 검증하는 서비스
	 * 
	 * @param user 사용자 정보
	 */
	private void validateUser(User user) {
		if (userRepository.existsByUserId(user.getUserId())) {
			throw new CustomException(ErrorCode.NAME_ALREADY_EXISTS, user.getUserId());
		}

		if (isNullOrBlank(user.getUserId())) {
			throw new CustomException(ErrorCode.MEMBER_IDS_IS_EMPTY_OR_NULL, user.getUserId());
		}

		if (isNullOrBlank(user.getUserPwd())) {
			throw new CustomException(ErrorCode.MEMBER_PWD_IS_EMPTY_OR_NULL, user.getUserPwd());
		}

		if (isValidEmail(user.getEmail())) {
			throw new CustomException(ErrorCode.INVALID_EMAIL, user.getEmail());
		}
	}

	/**
	 * 주어진 문자열이 null이거나 공백인지 확인하는 서비스
	 * 
	 * @param str 확인할 문자열
	 * @return 주어진 문자열이 null이거나 공백이면 true, 아니면 false
	 */
	private boolean isNullOrBlank(String str) {
		return str == null || str.isEmpty();
	}

	/**
	 * 주어진 이메일 주소가 유효한 형식인지 확인하는 서비스
	 * 
	 * @param email 확인할 이메일 주소
	 * @return 유효한 이메일 주소면 true, 아니면 false
	 */
	private boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);
		return !matcher.matches();
	}

}
