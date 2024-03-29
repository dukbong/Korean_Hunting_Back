package com.hangulhunting.Korean_Hunting.entity.enumpackage;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 삭제 예정 파일을 저장하지 않음
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