package com.hangulhunting.Korean_Hunting.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hangulhunting.Korean_Hunting.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	void deleteByValue(String refreshToken);
	Optional<RefreshToken> findByUserEntityId(Long userId);
	Optional<RefreshToken> findByValue(String refreshToken);
}