package com.hangulhunting.Korean_Hunting.serviceImpl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hangulhunting.Korean_Hunting.dto.ZipFile;
import com.hangulhunting.Korean_Hunting.entity.enumpackage.ExtractionStrategyType;
import com.hangulhunting.Korean_Hunting.entity.enumpackage.FileStatus;
import com.hangulhunting.Korean_Hunting.entity.enumpackage.FileType;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;
import com.hangulhunting.Korean_Hunting.service.ExtractionStrategy;
import com.hangulhunting.Korean_Hunting.serviceImpl.file.CommentRemover;
import com.hangulhunting.Korean_Hunting.serviceImpl.file.ExtractionStrategyProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

	private final CommentRemover commentRemover;
	private final ExtractionStrategyProvider extractionStrategyProvider;

	/***
	 * 사용자가 원하는 파일에서 원하는 내용을 찾아주는 서비스
	 * 
	 * @param file                   사용자가 제공한 파일
	 * @param extractionStrategyType 파일에 적용할 전략
	 * @return 사용자가 원하는 내용이 포함된 파일 구조 및 텍스트
	 */
	public ZipFile searchInFile(MultipartFile file, ExtractionStrategyType extractionStrategyType) {
		ZipFile zipFile = null;
		try {
			zipFile = processFileStructure(file, extractionStrategyType);
		} catch (IOException e) {
			log.error("Error while processing file structure: {}", e.getMessage());
			throw new CustomException(ErrorCode.FILE_PROCESSING_ERROR);
		}
		return zipFile;
	}

	private ZipFile processFileStructure(MultipartFile file, ExtractionStrategyType extractionStrategyType)
			throws IOException {
		ZipFile zipFile = new ZipFile();
		ArrayList<String> directory = new ArrayList<>();
	
		try (ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(file.getInputStream()), StandardCharsets.UTF_8)) {
			ZipEntry zipEntry;
			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				if (!zipEntry.isDirectory()) {
					processZipEntry(zipInputStream, zipEntry, directory, zipFile, extractionStrategyType);
				}
				zipInputStream.closeEntry();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new CustomException(ErrorCode.FILE_UNZIP_ERROR);
		}
		zipFile.setDirectory(directory);
		return zipFile;
	}

//	private ZipFile processFileStructure(MultipartFile file, ExtractionStrategyType extractionStrategyType)
//			throws IOException {
//		ZipFile zipFile = new ZipFile();
//		ArrayList<String> directory = new ArrayList<>();
//
//		try (ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream(), StandardCharsets.UTF_8);
//				BufferedInputStream bufferedInputStream = new BufferedInputStream(zipInputStream)) {
//
//			ZipEntry zipEntry;
//
//			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
//				if (!zipEntry.isDirectory()) {
//					processZipEntry(bufferedInputStream, zipEntry, directory, zipFile, extractionStrategyType);
//					zipInputStream.closeEntry();
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new CustomException(ErrorCode.FILE_UNZIP_ERROR);
//		}
//
//		zipFile.setDirectory(directory);
//		return zipFile;
//	}

	private void processZipEntry(InputStream zipInputStream, ZipEntry zipEntry, ArrayList<String> directory, ZipFile zipFile, ExtractionStrategyType extractionStrategyType)
			throws IOException {
		for (FileType fileType : FileType.values()) {
			if (zipEntry.getName().endsWith(fileType.getValue())) {
				String contentWithoutComments = commentRemover.removeComments(zipInputStream, fileType.getValue());
				ExtractionStrategy extractionStrategy = extractionStrategyProvider
						.setExtractionStrategy(extractionStrategyType);
				Set<String> words = extractionStrategy.extract(contentWithoutComments);
				if (search(zipEntry.getName(), words, zipFile)) {
					directory.add(zipEntry.getName() + FileStatus._$INSERT);
				} else {
					directory.add(zipEntry.getName());
				}
			}
		}
		directory.add(zipEntry.getName());
	}

	private boolean search(String filePath, Set<String> words, ZipFile zipFile) {
		if (!words.isEmpty()) {
			if (zipFile.getContent() != null) {
				byte[] result = writeSearchResultToByteArray(words, filePath);
				byte[] existingContent = zipFile.getContent();
				byte[] combinedContent = combineByteArrays(existingContent, result);
				zipFile.setContent(combinedContent);
			} else {
				zipFile.setContent(writeSearchResultToByteArray(words, filePath));
			}
			return true;
		}
		return false;
	}

	private byte[] combineByteArrays(byte[] array1, byte[] array2) {
		ByteBuffer combinedBuffer = ByteBuffer.allocate(array1.length + array2.length);
		combinedBuffer.put(array1);
		combinedBuffer.put(array2);
		return combinedBuffer.array();
	}

	private byte[] writeSearchResultToByteArray(Set<String> words, String filePath) {
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(bos, 4096)) {

			bos.write(filePath.getBytes(StandardCharsets.UTF_8));
			bos.write(System.lineSeparator().getBytes(StandardCharsets.UTF_8));

			int count = 1;
			for (String word : words) {
				String line = (count++) + ". " + word + System.lineSeparator();
				bufferedOutputStream.write(line.getBytes(StandardCharsets.UTF_8));
			}
			bufferedOutputStream.flush();
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw new CustomException(ErrorCode.FILE_WRITE_ERROR);
		}
	}

}
