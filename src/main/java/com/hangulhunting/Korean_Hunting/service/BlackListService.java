package com.hangulhunting.Korean_Hunting.service;

import org.springframework.stereotype.Service;

import com.hangulhunting.Korean_Hunting.repository.BlackListRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlackListService {

	private final BlackListRepository blackListRepository;
	
	public boolean existsByToken(String token) {
		return blackListRepository.existsByToken(token);
	}

}
