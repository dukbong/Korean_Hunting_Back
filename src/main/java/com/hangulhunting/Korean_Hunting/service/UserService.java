package com.hangulhunting.Korean_Hunting.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.hangulhunting.Korean_Hunting.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
	
	// Id Check
	public boolean userIdCheck(String userId) {
		return userRepository.existsByuserId(userId);
	}


	public boolean existsByUserIdIsNullOrBlank(String userId) {
		return existsAnyNullOrBlank(userId);
	}
	
	public boolean existsByUserPwdIsNullOrBlank(String userPwd) {
		return existsAnyNullOrBlank(userPwd);
	}
	
	private boolean existsAnyNullOrBlank(String str) {
		if(str == null || str.isEmpty()) {
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
}
