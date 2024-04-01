package com.hangulhunting.Korean_Hunting.file.util;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;

public class WordExtractorUtil {
    /***
     * 주어진 정규식 패턴에 해당하는 단어를 추출하는 유틸리티 메서드
     * @param contentWithoutComments 주석이 제거된 파일 내용
     * @param regex 정규식 패턴
     * @param groupIndex 추출할 그룹 인덱스
     * @return 추출된 단어들의 Set
     */
	
	// 어떤걸 쓰던 아직 큰 성능 차이를 알 수 없다.
//    public static Set<String> extractWords(String contentWithoutComments, String regex, int groupIndex) {
//        Set<String> words = new HashSet<>();
//        try (BufferedReader reader = new BufferedReader(new StringReader(contentWithoutComments), 3072)) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                Pattern pattern = Pattern.compile(regex);
//                Matcher matcher = pattern.matcher(line);
//                while (matcher.find()) {
//                    String word = matcher.group(groupIndex).trim();
//                    if (!word.isEmpty()) {
//                        words.add(word);
//                    }
//                }
//            }
//        } catch (Exception e) {
//        	throw new CustomException(ErrorCode.FILE_EXTRACT_WORD);
//        }
//        return words;
//    }
	
    public static Set<String> extractWords(String contentWithoutComments, String regex, int groupIndex) {
    	Set<String> words = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(contentWithoutComments), 4096)) {
            Stream<String> lines = reader.lines().parallel(); // 병렬 스트림으로 변경하여 데이터를 병렬로 처리합니다.
            Pattern pattern = Pattern.compile(regex); // 정규식 패턴을 컴파일하여 재사용합니다.
            lines.forEach(line -> {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    String word = matcher.group(groupIndex).trim();
                    if (!word.isEmpty()) {
                        words.add(word);
                    }
                }
            });
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FILE_EXTRACT_WORD);
        }
        return words;
    }
}
