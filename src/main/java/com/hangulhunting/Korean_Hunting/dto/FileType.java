package com.hangulhunting.Korean_Hunting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FileType {
	
	JSP(".jsp");

    private final String value;

    public String getValue() {
        return value;
    }
}
