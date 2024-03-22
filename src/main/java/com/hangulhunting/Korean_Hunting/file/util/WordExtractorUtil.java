package com.hangulhunting.Korean_Hunting.file.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordExtractorUtil {
    /***
     * 주어진 정규식 패턴에 해당하는 단어를 추출하는 유틸리티 메서드
     * @param contentWithoutComments 주석이 제거된 파일 내용
     * @param regex 정규식 패턴
     * @param groupIndex 추출할 그룹 인덱스
     * @return 추출된 단어들의 Set
     */
    public static Set<String> extractWords(String contentWithoutComments, String regex, int groupIndex) {
        Set<String> words = new HashSet<>();
        String[] lines = contentWithoutComments.split("\n");
        for (String line : lines) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                String word = matcher.group(groupIndex).trim();
                if (!word.isEmpty()) {
                    words.add(word);
                }
            }
        }
        return words;
    }
}
