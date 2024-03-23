package com.hangulhunting.Korean_Hunting.serviceImpl.file;

import org.springframework.stereotype.Component;

import com.hangulhunting.Korean_Hunting.entity.enumpackage.ExtractionStrategyType;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;
import com.hangulhunting.Korean_Hunting.service.ExtractionStrategy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExtractionStrategyProvider {
	
	private final KoreanWordExtractionStrategy koreanWordExtractionStrategy;
	private final TagTextExtractionStrategy tagTextExtractionStrategy;

	public ExtractionStrategy setExtractionStrategy(ExtractionStrategyType strategyType) {
		switch (strategyType){
		case EXTRACTION_KOREAN:
			return koreanWordExtractionStrategy;
		case EXTRACTION_TAG:
			return tagTextExtractionStrategy;
		default :
			throw new CustomException(ErrorCode.FILE_STRATEGY_ERROR);
		}
	}
}