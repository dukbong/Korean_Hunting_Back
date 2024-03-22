package com.hangulhunting.Korean_Hunting.entity.enumpackage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExtractionStrategyType {

	EXTRACTION_KOREAN("extraction_korea"),
	EXTRACTION_TAG("extraction_tag");
	
	private String value;

}
