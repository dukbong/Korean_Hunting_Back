package com.hangulhunting.Korean_Hunting.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.dto.UserResDto;
import com.hangulhunting.Korean_Hunting.dto.UserRole;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder; 
	
	private boolean existsAnyNullOrBlank(String str) {
		if(str == null || str.isEmpty()) {
			return true;
		}
		return false;
	}

	private boolean isValidEmail(String email) {
	    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
	    Pattern pattern = Pattern.compile(emailRegex);
	    Matcher matcher = pattern.matcher(email);
	    return !matcher.matches();
	}


	public UserResDto joinProcess(User user) {
		if(userRepository.existsByuserId(user.getUserId()))
			throw new CustomException(ErrorCode.NAME_ALREADY_EXISTS,user.getUserId());
		
		if(existsAnyNullOrBlank(user.getUserId()))
			throw new CustomException(ErrorCode.MEMBER_IDS_IS_EMPTY_OR_NULL, "아이디는 Null이거나 빈문자이 될 수 없습니다.");
		
		if(existsAnyNullOrBlank(user.getUserPwd()))
			throw new CustomException(ErrorCode.MEMBER_PWD_IS_EMPTY_OR_NULL, "비밀번호는 Null이거나 빈문자이 될 수 없습니다.");
		
		if(isValidEmail(user.getEmail()))
			throw new CustomException(ErrorCode.INVALID_EMAIL, "이메일이 유효하지 않습니다.");
		
		UserEntity userEntity = UserEntity.builder()
				  						  .userId(user.getUserId())
				  						  .userPwd(user.getUserPwd())
				  						  .email(bCryptPasswordEncoder.encode(user.getEmail()))
				  						  .company(user.getCompany())
				  						  .role(UserRole.ROLE_USER)
				  						  .build();
		userRepository.save(userEntity);
		
		return new UserResDto("회원가입에 성공하였습니다.");
	}
}
