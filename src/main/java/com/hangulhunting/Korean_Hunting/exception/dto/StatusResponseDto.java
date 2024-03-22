package com.hangulhunting.Korean_Hunting.exception.dto;

import com.hangulhunting.Korean_Hunting.exception.ErrorCode;

public class StatusResponseDto extends RuntimeException {

	private static final long serialVersionUID = 596424250936373387L;
	
	ErrorCode errorCode;
}
