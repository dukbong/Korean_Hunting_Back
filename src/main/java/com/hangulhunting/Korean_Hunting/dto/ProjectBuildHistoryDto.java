package com.hangulhunting.Korean_Hunting.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectBuildHistoryDto {

	private Long id;
	
	private String projectName;
	
	private LocalDateTime buildTime;
	
	private boolean status;
	
}
