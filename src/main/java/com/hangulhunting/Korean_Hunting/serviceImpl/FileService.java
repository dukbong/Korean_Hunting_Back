package com.hangulhunting.Korean_Hunting.serviceImpl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

	public ZipFile searchInFile(MultipartFile file, ExtractionStrategyType extractionStrategyType) {
		ZipFile zipFile = new ZipFile();
		List<String> directory = new ArrayList<>();
//		List<ZipEntry> zipEntries = new ArrayList<>(); // 2차 방식
		try (ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(file.getInputStream() /*1MB 1048576*/), StandardCharsets.UTF_8)) {
			ZipEntry zipEntry;
			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				if (!zipEntry.isDirectory()) {
//					zipEntries.add(zipEntry); // 2차 방식
					processZipEntry(zipInputStream, zipEntry, directory, zipFile, extractionStrategyType); // 1차 방식
				}
				zipInputStream.closeEntry(); // 1차 방식
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new CustomException(ErrorCode.FILE_UNZIP_ERROR);
		}
		// 2차 방식 병렬 ---------------
//		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//	    List<Future<?>> futures = new ArrayList<>();
//
//	    for (ZipEntry zipEntry : zipEntries) {
//	        futures.add(executorService.submit(() -> processZipEntry(zipEntry, directory, zipFile, extractionStrategyType)));
//	    }
//
//	    // 모든 작업이 완료될 때까지 대기
//	    for (Future<?> future : futures) {
//	        try {
//	            future.get();
//	        } catch (InterruptedException | ExecutionException e) {
//	            e.printStackTrace();
//	            throw new CustomException(ErrorCode.FILE_PROCESSING_ERROR);
//	        }
//	    }
//
//	    executorService.shutdown();
	    // 2차 방식 병렬 ---------------
		zipFile.setDirectory(directory);
		return zipFile;
	}
	
	// 2차 방식
//	private void processZipEntry(ZipEntry zipEntry, List<String> directory, ZipFile zipFile, ExtractionStrategyType extractionStrategyType) {
//		for (FileType fileType : FileType.values()) {
//			if (zipEntry.getName().endsWith(fileType.getValue())) {
//				String contentWithoutComments = commentRemoverVersionUp.removeComments(zipEntry, fileType.getValue());
//				ExtractionStrategy extractionStrategy = extractionStrategyProvider
//						.setExtractionStrategy(extractionStrategyType);
//				Set<String> words = extractionStrategy.extract(contentWithoutComments);
//				if (search(zipEntry.getName(), words, zipFile)) {
//					directory.add(zipEntry.getName() + FileStatus._$INSERT);
//				} else {
//					directory.add(zipEntry.getName());
//				}
//			}
//		}
//		directory.add(zipEntry.getName());
//	}

	// O(N)
	// 1차 방식 : FileType의 수가 적기 때문에 별도의 알고리즘 적용 x 추후 생각
	private void processZipEntry(InputStream zipInputStream, ZipEntry zipEntry, List<String> directory, ZipFile zipFile, ExtractionStrategyType extractionStrategyType)
			throws IOException {
		for (FileType fileType : FileType.values()) {
			if (zipEntry.getName().endsWith(fileType.getValue())) {
				byte[] buffer = sizeBuffer(zipEntry.getSize());
				String contentWithoutComments = commentRemover.removeComments(zipInputStream, buffer, fileType.getValue());
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

//	private byte[] sizeBuffer(long fileSize) {
//	    // 파일 크기를 기준으로 버퍼 크기를 설정합니다.
//	    if (fileSize <= 1024) {
//	        return new byte[1024]; // 1KB
//	    } else if (fileSize <= 2048) {
//	        return new byte[2048]; // 2KB
//	    } else if (fileSize <= 3072) {
//	        return new byte[3072]; // 3KB
//	    } else {
//	        return new byte[8192]; // 8KB
//	    } 
//	}
	
	private byte[] sizeBuffer(long fileSize) {
	    int bufferSize = 1024;
	    if (fileSize > 1024) {
	        bufferSize = (int) Math.pow(2, Math.ceil(Math.log(fileSize) / Math.log(2)));
	    }
	    return new byte[bufferSize];
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
	    byte[] combined = new byte[array1.length + array2.length];
	    System.arraycopy(array1, 0, combined, 0, array1.length);
	    System.arraycopy(array2, 0, combined, array1.length, array2.length);
	    return combined;
	}

	private byte[] writeSearchResultToByteArray(Set<String> words, String filePath) {
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(bos, 1024)) {

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
