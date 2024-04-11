package com.hangulhunting.Korean_Hunting.entity.enumpackage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Regex {

	EMAIL_REGEX("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$"),
	UPPERCASE(".*[A-Z].*"),
	LOWERCASE(".*[a-z].*"),
	SPECIAL_CHARACTER(".*[!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*");
	
	private String regex;
	
}
