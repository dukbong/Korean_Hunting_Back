package com.hangulhunting.Korean_Hunting.file.util;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WordExtractorUtil {
	// O(N) 한줄씩
	public static Set<String> extractWords(String contentWithoutComments, String regex, int groupIndex) {
		Set<String> words = ConcurrentHashMap.newKeySet();
        ExecutorService executorService = Executors.newCachedThreadPool();
	    try (BufferedReader reader = new BufferedReader(new StringReader(contentWithoutComments), 1024)) {
	        String line;
	        Pattern pattern = Pattern.compile(regex); // 정규식 패턴을 컴파일하여 재사용합니다.
	        while ((line = reader.readLine()) != null) {
//	            Matcher matcher = pattern.matcher(line);
//	            while (matcher.find()) {
//	                String word = matcher.group(groupIndex).trim();
//	                if (!word.isEmpty()) {
//	                    words.add(word);
//	                }
//	            }
	        	final String finalLine = line;
	        	executorService.submit(() -> {
                    Matcher matcher = pattern.matcher(finalLine);
                    while (matcher.find()) {
                        String word = matcher.group(groupIndex).trim();
                        if (!word.isEmpty()) {
                        	words.add(word);
                        }
                    }
                });
	        }
	    } catch (Exception e) {
	        throw new CustomException(ErrorCode.FILE_EXTRACT_WORD);
	    } finally {
	    	executorService.shutdown();
	    }
	    return words;
	}
}
