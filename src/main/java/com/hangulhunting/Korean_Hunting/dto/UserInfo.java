package com.hangulhunting.Korean_Hunting.dto;

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
	
	@Builder
	public UserInfo(String userId,String email, String company, String apiToken) {
		this.userId = userId;
		this.email = email;
		this.company = company;
		this.apiToken = apiToken;
	}
}