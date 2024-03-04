package com.hangulhunting.Korean_Hunting.service;

import org.springframework.stereotype.Service;

import com.hangulhunting.Korean_Hunting.dto.User;
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
}
