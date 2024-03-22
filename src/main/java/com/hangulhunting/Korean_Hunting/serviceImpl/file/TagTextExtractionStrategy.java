package com.hangulhunting.Korean_Hunting.serviceImpl.file;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.hangulhunting.Korean_Hunting.file.util.WordExtractorUtil;
import com.hangulhunting.Korean_Hunting.service.ExtractionStrategy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TagTextExtractionStrategy implements ExtractionStrategy {
	
	
    /***
     * 주어진 파일 내에서 주석을 제거하고 태그 안의 텍스트를 추출하는 서비스
     * 
     * @param fileContent 파일 내용
     * @return 추출된 태그 텍스트의 Set
     */
	@Override
	public Set<String> extract(String fileContent) {
        return WordExtractorUtil.extractWords(fileContent, "(?s)>(.*?)<", 1);
	}
}
