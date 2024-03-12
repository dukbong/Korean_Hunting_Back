package com.hangulhunting.Korean_Hunting.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hangulhunting.Korean_Hunting.entity.RefreshToken;
import com.hangulhunting.Korean_Hunting.repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;

	public void deleteByValue(String token) {
		refreshTokenRepository.deleteByValue(token);
	}

	public Optional<RefreshToken> findByValue(String refreshToken) {
		return refreshTokenRepository.findByValue(refreshToken);
	}
	
}
