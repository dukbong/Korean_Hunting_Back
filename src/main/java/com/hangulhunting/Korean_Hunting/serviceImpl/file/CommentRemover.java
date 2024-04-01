package com.hangulhunting.Korean_Hunting.serviceImpl.file;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = zipInputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			Pattern pattern = commentPattern.get(fileType);
			String fileContent = new String(outputStream.toByteArray(), StandardCharsets.UTF_8);
			Matcher matcher = pattern.matcher(fileContent);
			return matcher.replaceAll("");
		} catch (IOException e) {
			throw new CustomException(ErrorCode.FILE_REMOVE_COMMENT);
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				log.error("ByteArrayOutputStream close error : {}", e);
			}
		}
	}

}
