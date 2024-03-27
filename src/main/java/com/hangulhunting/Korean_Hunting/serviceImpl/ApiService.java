package com.hangulhunting.Korean_Hunting.serviceImpl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hangulhunting.Korean_Hunting.entity.ApiTokenEntity;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;
import com.hangulhunting.Korean_Hunting.repository.ApiTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApiService {

	private final ApiTokenRepository apiTokenRepository;
	
	/***
	 * Redis를 사용하면 효율적일 꺼 같다.
	 * @param token
	 */
	public void apiTokenCheck(String token) {
		Optional<ApiTokenEntity> findApiTokenEntity = apiTokenRepository.findByApiToken(token);
		if(!findApiTokenEntity.isPresent()) {
			throw new CustomException(ErrorCode.API_TOKEN_NOT_FOUND);
		}
	}
	
}
