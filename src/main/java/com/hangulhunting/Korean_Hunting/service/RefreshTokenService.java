package com.hangulhunting.Korean_Hunting.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hangulhunting.Korean_Hunting.entity.RefreshToken;
import com.hangulhunting.Korean_Hunting.repository.RefreshTokenRepository;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;
	private final UserRepository userRepository;

	public void deleteByValue(String token) {
		refreshTokenRepository.deleteByValue(token);
	}

	public Optional<RefreshToken> findByValue(String refreshToken) {
		return refreshTokenRepository.findByValue(refreshToken);
	}

	public void save(RefreshToken refreshToken) {
		refreshTokenRepository.save(refreshToken);
	}

	public Optional<RefreshToken> findByUserId(String username) {
		return refreshTokenRepository.findByUserEntityId(userRepository.findByUserId(username).get().getId());
//		return null;
	}
	
}
