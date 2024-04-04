package com.hangulhunting.Korean_Hunting.serviceImpl;

import org.springframework.stereotype.Service;

import com.hangulhunting.Korean_Hunting.entity.ProjectBuildHistory;
import com.hangulhunting.Korean_Hunting.repository.ProjectBuildHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectBuildHistoryService {

	private final ProjectBuildHistoryRepository projectBuildHistoryRepository;
	
	public void projectBuild(ProjectBuildHistory projectBuildHistory) {
		projectBuildHistoryRepository.save(projectBuildHistory);
	}
}
