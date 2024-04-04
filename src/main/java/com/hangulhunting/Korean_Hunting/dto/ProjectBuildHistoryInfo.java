package com.hangulhunting.Korean_Hunting.dto;

import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectBuildHistoryInfo {

	private int totalPage;
	
	private Page<ProjectBuildHistoryDto> projectBuildHistorys;
}
