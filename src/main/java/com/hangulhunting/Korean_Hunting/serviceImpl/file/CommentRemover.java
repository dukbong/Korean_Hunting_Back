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
	
	public String removeComments(InputStream zipInputStream, String fileType) {
	    try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	         BufferedInputStream bufferedInputStream = new BufferedInputStream(zipInputStream)) {
	        byte[] buffer = new byte[4096]; // 버퍼 크기를 조정하여 성능 향상
	        int bytesRead;
	        while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
	            outputStream.write(buffer, 0, bytesRead);
	        }
	        String fileContent = outputStream.toString(StandardCharsets.UTF_8);
	        Pattern pattern = commentPattern.get(fileType);
	        Matcher matcher = pattern.matcher(fileContent);

	        StringBuffer result = new StringBuffer(); // Matcher의 appendReplacement 및 appendTail을 사용하기 위한 StringBuffer

	        int lastEnd = 0;
	        while (matcher.find()) {
	            result.append(fileContent, lastEnd, matcher.start());
	            lastEnd = matcher.end();
	        }
	        result.append(fileContent, lastEnd, fileContent.length());
	        return result.toString();
	    } catch (IOException e) {
	        throw new CustomException(ErrorCode.FILE_REMOVE_COMMENT);
	    }
	}

}
