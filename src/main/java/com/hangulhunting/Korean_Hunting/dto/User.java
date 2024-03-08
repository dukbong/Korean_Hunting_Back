package com.hangulhunting.Korean_Hunting.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class User {
	
	private String userId;
	private String userPwd;
	private String email;
	private String company;
	
	public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(userId, userPwd);
    }

	@Builder
	public User(String userId, String userPwd, String email, String company) {
		this.userId = userId;
		this.userPwd = userPwd;
		this.email = email;
		this.company = company;
	}
}
