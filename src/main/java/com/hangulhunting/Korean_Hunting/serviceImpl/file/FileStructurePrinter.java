package com.hangulhunting.Korean_Hunting.serviceImpl.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.hangulhunting.Korean_Hunting.entity.enumpackage.ExtractionStrategyType;
import com.hangulhunting.Korean_Hunting.entity.enumpackage.FileStatus;
import com.hangulhunting.Korean_Hunting.entity.enumpackage.FileType;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;
import com.hangulhunting.Korean_Hunting.service.ExtractionStrategy;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FileStructurePrinter {
	
	private final ExtractionStrategyProvider extractionStrategyProvider;
	private final CommentRemover commentRemover;
	/***
	 * 주어진 디렉토리의 파일 구조를 반환 하는 서비스
	 * 
	 * @param directoryPath 파일 구조를 출력할 디렉토리 경로
	 * @param fileTypes 처리할 파일 유형
	 * @return 파일 구조를 나타내는 문자열 목록
	 */
	public ArrayList<String> printDirectory(Path directoryPath, FileType[] fileTypes, ExtractionStrategyType extractionStrategyType) {
		ArrayList<String> fileStructure = new ArrayList<>();
		try (Stream<Path> paths = Files.walk(directoryPath)) {
			Path root = directoryPath;
			paths.filter(Files::isRegularFile)
				 .map(path -> root.relativize(path))
				 .map(Path::toString)
				 .forEach(filePath -> processFile(directoryPath, filePath, fileTypes, fileStructure, extractionStrategyType));
			Collections.reverse(fileStructure);
		} catch (IOException e) {
			throw new CustomException(ErrorCode.FILE_STRUCTURE_ERROR);
		}
		return fileStructure;
	}

	/***
	 * 파일을 처리하여 파일 구조를 업데이트하는 서비스
	 * 
	 * @param directoryPath 처리 중인 디렉토리 경로
	 * @param filePath 처리 중인 파일 경로
	 * @param fileTypes 처리할 파일 유형
	 * @param fileStructure 파일 구조를 나타내는 문자열 목록
	 * @param extractionStrategyType 
	 */
	private void processFile(Path directoryPath, String filePath, FileType[] fileTypes, ArrayList<String> fileStructure, ExtractionStrategyType extractionStrategyType) {
		for (FileType type : fileTypes) {
			if (isFileType(filePath, type)) {
				fileStructure.add(appendInsertStatus(filePath, directoryPath, type, extractionStrategyType));
				break;
			}
		}
	}

	/***
	 * 파일의 유형이 지정된 유형과 일치하는지 확인하는 서비스
	 * 
	 * @param filePath 파일 경로
	 * @param fileType 처리할 파일 유형
	 * @return 파일의 유형이 일치하면 true를 반환하고, 그렇지 않으면 false를 반환합니다.
	 */
	private boolean isFileType(String filePath, FileType fileType) {
		return filePath.endsWith(fileType.getValue());
	}

	/***
	 * FilePrinterMethod를 사용하여 문서를 처리하고 삽입 상태를 추가하는 서비스
	 * 
	 * @param filePath 파일 경로
	 * @param directoryPath 처리 중인 디렉토리 경로
	 * @param fileType 처리할 파일 유형
	 * @param extractionStrategyType 
	 * @return 삽입 상태가 추가된 파일 경로
	 */
	private String appendInsertStatus(String filePath, Path directoryPath, FileType fileType, ExtractionStrategyType extractionStrategyType) {
		String fileContent = getFileContent(directoryPath, filePath);
		String contentWithoutComments = commentRemover.removeComments(fileContent, fileType.getValue());
		// 전략 패턴 사용
		ExtractionStrategy extractionStrategy = extractionStrategyProvider.setExtractionStrategy(extractionStrategyType);
		Set<String> words = extractionStrategy.extract(contentWithoutComments);
		if (search(directoryPath, filePath, words)) {
			filePath += FileStatus._$INSERT;
		}
		return filePath;
	}

	/***
	 * 파일의 내용을 문자열로 읽어오는 서비스
	 * 
	 * @param directoryPath 처리 중인 디렉토리 경로
	 * @param fileName 처리 중인 파일 경로
	 * @return 파일의 내용을 나타내는 문자열
	 */
	private String getFileContent(Path directoryPath, String fileName) {
		StringBuilder fileContentBuilder = new StringBuilder();
		Path checkFilePath = directoryPath.resolve(fileName);
		try (Stream<String> lines = Files.lines(checkFilePath)) {
			lines.forEach(line -> fileContentBuilder.append(line).append("\n"));
		} catch (IOException e) {
			throw new CustomException(ErrorCode.FILE_READ_ERROR);
		}
		return fileContentBuilder.toString();
	}

	/***
	 * 주어진 단어 목록이 비어있지 않으면 검색 결과를 확인하고 파일에 저장하는 서비스
	 * 
	 * @param directoryPath 검색 중인 디렉토리 경로
	 * @param fileName 검색 중인 파일 경로
	 * @param words 검색된 단어의 집합
	 * @return 검색된 단어가 있으면 true, 그렇지 않으면 false를 반환합니다.
	 */
	private boolean search(Path directoryPath, String fileName, Set<String> words) {
		if (!words.isEmpty()) {
			writeSearchResultToFile(words, directoryPath, fileName);
			return true;
		}
		return false;
	}

	/***
	 * FilePrinterMethod를 사용하여 검색 결과를 확인하고 파일로 저장하는 서비스
	 * 
	 * @param words 검색 결과로 발견된 한글 단어 집합
	 * @param directoryPath 처리 중인 디렉토리 경로
	 * @param fileName 처리 중인 파일 경로
	 */
	private void writeSearchResultToFile(Set<String> words, Path directoryPath, String fileName) {
		if (!words.isEmpty()) {
			StringBuilder contentBuilder = new StringBuilder();
			String koreanFilePath = directoryPath.resolve("text_package.txt").toString();
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(koreanFilePath, true));) {
				bw.write(fileName + "\n");
				int index = 1;
				for (String word : words) {
					contentBuilder.append(index++).append(". ").append(word).append("\n");
				}
				bw.write(contentBuilder.toString());
			} catch (IOException e) {
				throw new CustomException(ErrorCode.FILE_WRITE_ERROR);
			}
		}
	}
}
