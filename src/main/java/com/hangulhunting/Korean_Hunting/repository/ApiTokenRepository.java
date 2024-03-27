package com.hangulhunting.Korean_Hunting.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hangulhunting.Korean_Hunting.entity.ApiTokenEntity;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;

public interface ApiTokenRepository extends JpaRepository<ApiTokenEntity, Long>{

	Optional<ApiTokenEntity> findByApiToken(String token);

	Optional<ApiTokenEntity> findByUserEntity(UserEntity userEntity);

	void deleteByUserEntity(UserEntity userEntity);

}
