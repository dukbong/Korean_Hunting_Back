package com.hangulhunting.Korean_Hunting.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/***
 * 로그인 정보를 담는 DTO
 * 
 * @field userId, userPwd, email, company
 * @method getter, toAuthentication
 * @constructor Builder
 * 
 */
@NoArgsConstructor
@Getter
public class User {
	
	private String userId;
	private String userPwd;
	private String email;
	private String company;
	private String joinRoute;
	
	public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(userId, userPwd);
    }

	@Builder
	public User(String userId, String userPwd, String email, String company, String joinRoute) {
		this.userId = userId;
		this.userPwd = userPwd;
		this.email = email;
		this.company = company;
		this.joinRoute = joinRoute;
	}
	
}
