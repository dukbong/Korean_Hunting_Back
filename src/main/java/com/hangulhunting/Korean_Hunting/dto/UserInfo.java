package com.hangulhunting.Korean_Hunting.dto;

import java.time.LocalDate;
import java.util.List;

import com.hangulhunting.Korean_Hunting.entity.ProjectBuildHistory;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserInfo {
	
	private String userId;
	private String email;
	private String company;
	private String apiToken;
	private LocalDate issuanceTime;
	private String tokenExpiresIn;
	private List<ProjectBuildHistoryDto> projectBuildHistory;
	
	@Builder
	public UserInfo(String userId,String email, String company, String apiToken, LocalDate issuanceTime, String tokenExpiresIn, List<ProjectBuildHistoryDto> projectBuildHistory) {
		this.userId = userId;
		this.email = email;
		this.company = company;
		this.apiToken = apiToken;
		this.issuanceTime = issuanceTime;
		this.tokenExpiresIn = tokenExpiresIn;
		this.projectBuildHistory = projectBuildHistory;
	}
}