package com.hangulhunting.Korean_Hunting.serviceImpl.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommentRemover {

	private final Map<String, Pattern> commentPattern = new HashMap<>();

	public CommentRemover() {
		commentPattern.put(".html", Pattern.compile("//.*|(?s)/\\*.*?\\*/|(?s)<!--.*?-->"));
		commentPattern.put(".jsp", Pattern.compile("(?s)<!--.*?-->|(?s)<%--.*?--%>|//.*|(?s)/\\*.*?\\*/"));
		commentPattern.put(".js", Pattern.compile("//.*|(?s)/\\*.*?\\*/"));
		commentPattern.put(".jsx", Pattern.compile("//.*|(?s)/\\*.*?\\*/"));
		commentPattern.put(".java", Pattern.compile("//.*|(?s)/\\*.*?\\*/"));
		commentPattern.put(".cs", Pattern.compile("//.*|(?s)/\\*.*?\\*/"));
		commentPattern.put(".py", Pattern.compile("#.*|(?s)\"\"\".*?\"\"\"|(?s)'''.*?'''"));
	}
	
	public String removeComments(byte[] contentWithoutCommentsBytes, String fileType) {
		// 데이터 크기가 4KB ~ 40KB 사이 일것으로 예상 되므로 Stream을 사용하지 않음
	    String fileContent = new String(contentWithoutCommentsBytes, StandardCharsets.UTF_8);
	    Pattern pattern = commentPattern.get(fileType);
	    Matcher matcher = pattern.matcher(fileContent);

	    StringBuilder result = new StringBuilder();

	    int lastEnd = 0;
	    while (matcher.find()) {
	        result.append(fileContent, lastEnd, matcher.start());
	        lastEnd = matcher.end();
	    }
	    result.append(fileContent, lastEnd, fileContent.length());
	    return result.toString();
	}


}
