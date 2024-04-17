package com.hangulhunting.Korean_Hunting.file.util;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WordExtractorUtil {
	
	public static Set<String> extractWords(String contentWithoutComments, String regex, int groupIndex) {
		Set<String> words = new HashSet<>();
	    try (BufferedReader reader = new BufferedReader(new StringReader(contentWithoutComments))) {
	        String line;
	        Pattern pattern = Pattern.compile(regex);
	        while ((line = reader.readLine()) != null) {
	            Matcher matcher = pattern.matcher(line);
	            while (matcher.find()) {
	                String word = matcher.group(groupIndex).trim();
	                if (!word.isEmpty()) {
	                    words.add(word);
	                }
	            }
	        }
	    } catch (Exception e) {
	        throw new CustomException(ErrorCode.FILE_EXTRACT_WORD);
	    }
	    return words;
	}
}
