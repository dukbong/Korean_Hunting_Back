package com.hangulhunting.Korean_Hunting.serviceImpl.file;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hangulhunting.Korean_Hunting.dto.ZipFile;
import com.hangulhunting.Korean_Hunting.dto.response.ApiResult;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;

@Component
public class AnalyzeFileContent {
	
	private AtomicInteger count = new AtomicInteger(0);
	
//	public ApiResult analyzeFileContent(ZipFile zipFile) {
//		int fileCount = 0;
//		List<String> resultList;
//		if (zipFile.getContent() != null) {
//			String content = new String(zipFile.getContent(), StandardCharsets.UTF_8);
//			AtomicInteger count = new AtomicInteger(0);
//			resultList = Stream.of(content.split("\n"))
//				.parallel() // 병렬 처리
//				.map(line -> {
//					if (line.matches("^\\d+\\. .*")) {
//						count.getAndIncrement(); // 숫자로 시작하고 . 뒤에 내용이 있는 패턴인 경우
//						return null;
//					} else {
//						return line;
//					}
//				})
//				.filter(line -> line != null)
//				.collect(Collectors.toList());
//			fileCount = count.get();
//		} else {
//			resultList = List.of(); // 빈 리스트 반환
//		}
//		return ApiResult.builder().count(fileCount).fileName(resultList).build();
//	}
	public ApiResult analyzeFileContent(ZipFile zipFile) {
	    int fileCount = 0;
	    List<String> resultList = new ArrayList<>();
	    if (zipFile.getContent() != null) {
	        try (InputStream inputStream = new ByteArrayInputStream(zipFile.getContent());
	             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8), 4096)) {
	        	ObjectMapper om = new ObjectMapper();
	        	JsonNode rootNode = om.readTree(reader);
	        	fileCount = countArrayElements(rootNode);
	        	extractKeys(rootNode, resultList); // 수정된 메서드 호출
	        	//	            reader.lines()
//	                  .parallel() // 병렬 처리
//	                  .forEach(line -> {
//	                      if (line.matches("^\\d+\\. .*")) {
//	                    	  count.getAndIncrement(); // 숫자로 시작하고 . 뒤에 내용이 있는 패턴인 경우
//	                      } else {
//	                          resultList.add(line);
//	                      }
//	                  });
//	            fileCount = count.get();
	        } catch (IOException e) {
	        	throw new CustomException(ErrorCode.FILE_READ_ERROR);
	        }
	    }
	    return ApiResult.builder().count(fileCount).fileName(resultList).build();
	}
	
	private int countArrayElements(JsonNode node) {
	    int count = 0;
	    Iterator<JsonNode> elements = node.elements();
	    while (elements.hasNext()) {
	        JsonNode element = elements.next();
	        if (element.isArray()) {
	            count += element.size();
	        }
	    }
	    return count;
	}
	
	private void extractKeys(JsonNode node, List<String> resultList) {
	    Iterator<String> fieldNames = node.fieldNames();
	    while (fieldNames.hasNext()) {
	        String fieldName = fieldNames.next();
	        resultList.add(fieldName); // 키만 리스트에 추가
	    }
	}
}
