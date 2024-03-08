package com.hangulhunting.Korean_Hunting.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hangulhunting.Korean_Hunting.entity.BlackList;

public interface BlackListRepository extends JpaRepository<BlackList, Long> {

	boolean existsByToken(String jwt);

}
