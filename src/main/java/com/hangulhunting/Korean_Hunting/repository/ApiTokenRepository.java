package com.hangulhunting.Korean_Hunting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hangulhunting.Korean_Hunting.entity.ApiTokenEntity;

public interface ApiTokenRepository extends JpaRepository<ApiTokenEntity, Long>{

}
