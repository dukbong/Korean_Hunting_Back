package com.hangulhunting.Korean_Hunting.service;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilePrinterMethod {
	
	private final CommentRemover commentRemover;
	
    /***
     * 주어진 파일 내에서 주석을 제거하고 한글 단어를 추출하는 서비스
     * @param fileContent 파일 내용
     * @param fileType 파일 유형
     * @return 추출된 한글 단어의 Set
     */
    public Set<String> extractKoreanWords(String fileContent, String fileType) {
        String contentWithoutComments = commentRemover.removeComments(fileContent, fileType);
        return extractWords(contentWithoutComments, "[가-힣\\s]+");
    }

    /***
     * 주어진 파일 내에서 주석을 제거하고 태그 안의 텍스트를 추출하는 서비스
     * 
     * @param fileContent 파일 내용
     * @param fileType 파일 유형
     * @return 추출된 태그 텍스트의 Set
     */
    public Set<String> extractTagText(String fileContent, String fileType) {
        String contentWithoutComments = commentRemover.removeComments(fileContent, fileType);
        return extractWords(contentWithoutComments, "(?s)>(.*?)<");
    }
    
    /***
     * 주어진 정규식 패턴에 해당하는 단어를 추출하는 서비스
     * @param contentWithoutComments 주석이 제거된 파일 내용
     * @param regex 정규식 패턴
     * @return 추출된 단어들의 Set
     */
    private Set<String> extractWords(String contentWithoutComments, String regex) {
        Set<String> words = new HashSet<>();
        String[] lines = contentWithoutComments.split("\n");
        for (String line : lines) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                String word = matcher.group().trim();
                if (!word.isEmpty()) {
                    words.add(word);
                }
            }
        }
        return words;
    }
	
}
