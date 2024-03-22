package com.hangulhunting.Korean_Hunting.entity.enumpackage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FileType {
	
	JSP(".jsp"), 
	PYTHON(".py"),
	CS(".cs"),
	JAVA(".java"),
	JS(".js"),
	HTML(".html");

    private final String value;

    public String getValue() {
        return value;
    }
}
