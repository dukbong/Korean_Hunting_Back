package com.hangulhunting.Korean_Hunting.serviceImpl.file;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hangulhunting.Korean_Hunting.dto.ZipFile;
import com.hangulhunting.Korean_Hunting.dto.response.ApiResult;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;

@Component
public class AnalyzeFileContent {
	
    private final ObjectMapper objectMapper;

    public AnalyzeFileContent(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
	
    public ApiResult analyzeFileContent(ZipFile zipFile) {
        int fileCount = 0;
        List<String> resultList = new ArrayList<>();
        if (zipFile.getContent() != null) {
            try (InputStream inputStream = new ByteArrayInputStream(zipFile.getContent())) {
                MappingIterator<ObjectNode> it = objectMapper.readerFor(ObjectNode.class)
                        									 .readValues(inputStream);
                while (it.hasNext()) {
                    ObjectNode node = it.next();
                    fileCount++;
                    extractKeys(node, resultList);
                }
            } catch (IOException e) {
                throw new CustomException(ErrorCode.FILE_READ_ERROR);
            }
        }
        return ApiResult.builder()
                		.count(fileCount)
                		.fileName(resultList)
                		.build();
    }

    private void extractKeys(ObjectNode node, List<String> resultList) {
        node.fieldNames().forEachRemaining(resultList::add);
    }
    
//	public ApiResult analyzeFileContent(ZipFile zipFile) {
//	    int fileCount = 0;
//	    List<String> resultList = new ArrayList<>();
//	    if (zipFile.getContent() != null) {
//	        try (InputStream inputStream = new ByteArrayInputStream(zipFile.getContent());
//	             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
//	        	JsonNode rootNode = objectMapper.readTree(reader);
//	        	fileCount = countArrayElements(rootNode);
//	        	extractKeys(rootNode, resultList);
//	        } catch (IOException e) {
//	        	throw new CustomException(ErrorCode.FILE_READ_ERROR);
//	        }
//	    }
//	    return ApiResult.builder()
//	    				.count(fileCount)
//	    				.fileName(resultList)
//	    				.build();
//	}
//	
//	private int countArrayElements(JsonNode node) {
//	    int count = 0;
//	    Iterator<JsonNode> elements = node.elements();
//	    while (elements.hasNext()) {
//	        JsonNode element = elements.next();
//	        if (element.isArray()) {
//	            count += element.size();
//	        }
//	    }
//	    return count;
//	}
//	
//	private void extractKeys(JsonNode node, List<String> resultList) {
//	    Iterator<String> fieldNames = node.fieldNames();
//	    while (fieldNames.hasNext()) {
//	        String fieldName = fieldNames.next();
//	        resultList.add(fieldName); // 키만 리스트에 추가
//	    }
//	}
}
