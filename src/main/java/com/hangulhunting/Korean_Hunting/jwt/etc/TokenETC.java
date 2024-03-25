package com.hangulhunting.Korean_Hunting.jwt.etc;

public class TokenETC {

	public static final String PREFIX = "Bearer ";
	public static final String BEARER_TYPE = "Bearer";
	public static final String AUTHORIZATION = "Authorization";
	public static final String AUTHORITIES_KEY = "auth";
	public static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1시간
	public static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 10;  // 10시간
	public static final long API_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일
	
}
