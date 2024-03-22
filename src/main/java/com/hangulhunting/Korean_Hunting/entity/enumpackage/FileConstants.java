package com.hangulhunting.Korean_Hunting.entity.enumpackage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FileConstants {
	
	SERVICE_FILE_NAME("koreaHuntingFolder"), 
	SERVICE_TEXT_FILE_NAME("text_package.txt");

    private final String value;

    public String getValue() {
        return value;
    }
}
