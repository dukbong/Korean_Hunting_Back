package com.hangulhunting.Korean_Hunting.serviceImpl;

import org.springframework.stereotype.Service;

import com.hangulhunting.Korean_Hunting.entity.BlackList;
import com.hangulhunting.Korean_Hunting.repository.BlackListRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlackListService {

	private final BlackListRepository blackListRepository;
	
	public boolean existsByToken(String token) {
		return blackListRepository.existsByToken(token);
	}

	public void save(String jwt) {
		BlackList blackList = BlackList.builder().token(jwt).build();
		blackListRepository.save(blackList);
	}

}
