package com.hangulhunting.Korean_Hunting.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hangulhunting.Korean_Hunting.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	public Optional<UserEntity> findByUserId(String userId);
	
	public boolean existsByUserId(String userId);
}
