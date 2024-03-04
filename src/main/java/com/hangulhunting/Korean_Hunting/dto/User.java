package com.hangulhunting.Korean_Hunting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User {
	
	private String userId;
	private String userPwd;
	private String email;
	private String company;
	
}