package com.hangulhunting.Korean_Hunting.service;

import org.springframework.stereotype.Service;

import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
}
