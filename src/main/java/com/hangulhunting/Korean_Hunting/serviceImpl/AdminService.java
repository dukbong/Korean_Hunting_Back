package com.hangulhunting.Korean_Hunting.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	
	private final UserRepository userRepository;

	public List<UserEntity> users() {
		return userRepository.findAll();
	}

}
