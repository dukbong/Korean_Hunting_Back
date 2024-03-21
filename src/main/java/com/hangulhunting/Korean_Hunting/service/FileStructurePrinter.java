package com.hangulhunting.Korean_Hunting.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.hangulhunting.Korean_Hunting.dto.FileStatus;
import com.hangulhunting.Korean_Hunting.dto.FileType;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStructurePrinter {
	
	private final FileStructureMethod fileStructureMethod;
	
	/***
	 * 파일의 구조를 반환하는 서비스
	 * @param uuidFolderPath
	 * @param fileType
	 * @return
	 */
	public ArrayList<String> printDirectory(Path uuidFolderPath, FileType[] fileType) {
		ArrayList<String> fileTree = new ArrayList<>();

		try (Stream<Path> paths = Files.walk(uuidFolderPath)) {
			Path root = uuidFolderPath;
			paths.filter(Files::isRegularFile).map(path -> root.relativize(path)).map(Path::toString)
					.forEach(filePath -> processFile(uuidFolderPath, filePath, fileType, fileTree));
			Collections.reverse(fileTree);
		} catch (IOException e) {
			throw new CustomException(ErrorCode.FILE_STRUCTURE_ERROR);
		}
		return fileTree;
	}

	/***
	 * 파일의 구조를 파악하는 서비스
	 * @param uuidFolderPath
	 * @param filePath
	 * @param fileType
	 * @param fileTree
	 */
	private void processFile(Path uuidFolderPath, String filePath, FileType[] fileType, ArrayList<String> fileTree) {
		for (FileType type : fileType) {
			if (isFileType(filePath, type)) {
				fileTree.add(appendInsertStatus(filePath, uuidFolderPath, type));
				break;
			}
		}
	}

	/***
	 * 파일의 타입이 처리가능한 타입인지 확인하는 서비스
	 * @param filePath
	 * @param fileType
	 * @return
	 */
	private boolean isFileType(String filePath, FileType fileType) {
		return filePath.endsWith(fileType.getValue());
	}

	/***
	 * FileStructureMethod에서 정한 방법으로 문서를 구분하는 서비스
	 * @param filePath
	 * @param uuidFolderPath
	 * @param fileType
	 * @return
	 */
	private String appendInsertStatus(String filePath, Path uuidFolderPath, FileType fileType) {
		String fileContent = getFileContent(uuidFolderPath, filePath);
		Set<String> words = fileStructureMethod.extractKoreanWords(fileContent, fileType.getValue());
		if (search(uuidFolderPath, filePath, words)) {
			filePath += FileStatus._$INSERT;
		}
		return filePath;
	}

	/***
	 * 파일을 읽어서 문자열로 저장하는 서비스
	 * @param uuidFolderPath
	 * @param targetFile
	 * @return
	 */
	public String getFileContent(Path uuidFolderPath, String targetFile) {
		StringBuilder sb = new StringBuilder();
		Path checkFilePath = uuidFolderPath.resolve(targetFile);
		try (Stream<String> lines = Files.lines(checkFilePath)) {
			lines.forEach(line -> sb.append(line).append("\n"));
		} catch (IOException e) {
			throw new CustomException(ErrorCode.FILE_READ_ERROR);
		}
		return sb.toString();
	}

	/***
	 * FileStructureMethod의 방법으로 찾은게 있는지 확인하는 서비스
	 * @param uuidFolderPath
	 * @param targetFile
	 * @param words
	 * @return
	 */
	private boolean search(Path uuidFolderPath, String targetFile, Set<String> words) {
		if (!words.isEmpty()) {
			searchFileWrite(words, uuidFolderPath, targetFile);
			return true;
		}
		return false;
	}

	/***
	 * FileStructureMethod의 방법으로 찾은 글자를 파일로 저장하는 서비스
	 * @param words
	 * @param uuidFolderPath
	 * @param targetFile
	 */
	private void searchFileWrite(Set<String> words, Path uuidFolderPath, String targetFile) {
		if (!words.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			String koreanFilePath = uuidFolderPath.resolve("text_package.txt").toString();
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(koreanFilePath, true));) {
				bw.write(targetFile + "\n");
				int index = 1;
				for (String word : words) {
					sb.append(index++).append(". ").append(word).append("\n");
				}
				bw.write(sb.toString());
			} catch (IOException e) {
				throw new CustomException(ErrorCode.FILE_WRITE_ERROR);
			}
		}
	}
}
