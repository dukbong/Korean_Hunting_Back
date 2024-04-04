package com.hangulhunting.Korean_Hunting.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hangulhunting.Korean_Hunting.entity.ProjectBuildHistory;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;

public interface ProjectBuildHistoryRepository extends JpaRepository<ProjectBuildHistory, Long> {
	Page<ProjectBuildHistory> findByUserEntity(UserEntity userEntity, Pageable pageable);
	
	int countByUserEntity(UserEntity userEntity);
}
