package com.hangulhunting.Korean_Hunting.serviceImpl.file;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.hangulhunting.Korean_Hunting.dto.ZipFile;
import com.hangulhunting.Korean_Hunting.dto.response.ApiResult;

@Component
public class AnalyzeFileContent {
	
	public ApiResult analyzeFileContent(ZipFile zipFile) {
		int fileCount = 0;
		List<String> resultList;
		if (zipFile.getContent() != null) {
			String content = new String(zipFile.getContent(), StandardCharsets.UTF_8);
			AtomicInteger count = new AtomicInteger(0);
			resultList = Stream.of(content.split("\n"))
				.parallel() // 병렬 처리
				.map(line -> {
					if (line.matches("^\\d+\\. .*")) {
						count.getAndIncrement(); // 숫자로 시작하고 . 뒤에 내용이 있는 패턴인 경우
						return null;
					} else {
						return line;
					}
				})
				.filter(line -> line != null)
				.collect(Collectors.toList());
			fileCount = count.get();
		} else {
			resultList = List.of(); // 빈 리스트 반환
		}
		return ApiResult.builder().count(fileCount).fileName(resultList).build();
	}
}
