package com.hangulhunting.Korean_Hunting.serviceImpl.file;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.hangulhunting.Korean_Hunting.file.util.WordExtractorUtil;
import com.hangulhunting.Korean_Hunting.service.ExtractionStrategy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KoreanWordExtractionStrategy implements ExtractionStrategy {
	
	private final CommentRemover commentRemover;
	
    /***
     * 주어진 파일 내에서 주석을 제거하고 한글 단어를 추출하는 서비스
     * @param fileContent 파일 내용
     * @param fileType 파일 유형
     * @return 추출된 한글 단어의 Set
     */
	@Override
	public Set<String> extract(String fileContent, String fileType) {
		String contentWithoutComments = commentRemover.removeComments(fileContent, fileType);
        return WordExtractorUtil.extractWords(contentWithoutComments, "[가-힣\\s]+", 0);
	}
}
