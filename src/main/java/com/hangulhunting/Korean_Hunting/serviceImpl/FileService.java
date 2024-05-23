package com.hangulhunting.Korean_Hunting.serviceImpl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static final ObjectMapper mapper = new ObjectMapper();
    
// I/O bound ->>>>>>>>>> Thread pool cancel
    @Async
    public CompletableFuture<ZipFile> searchInFileAsync(MultipartFile file, ExtractionStrategyType extractionStrategyType) {
        return CompletableFuture.supplyAsync(() -> searchInFile(file, extractionStrategyType), executorService);
    }

    public ZipFile searchInFile(MultipartFile file, ExtractionStrategyType extractionStrategyType) {
        Map<String, Set<String>> textContent = new HashMap<>();
        ZipFile zipFile = new ZipFile();
        List<String> directory = new ArrayList<>();

        try (ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(file.getInputStream()))) {

        	ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                	processZipEntry(zipInputStream, entry, directory, textContent, extractionStrategyType);
                }
                zipInputStream.closeEntry();
            }
        	
            zipFile.setContent(writeSearchResultToByteArray(textContent));
            zipFile.setDirectory(directory);
            return zipFile;

        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.FILE_UNZIP_ERROR);
        }
    }
    
    
	private void processZipEntry(ZipInputStream zipInputStream, ZipEntry zipEntry, List<String> directory, Map<String, Set<String>> textContent, ExtractionStrategyType extractionStrategyType)
			 {
		boolean found = false;
		String contentWithoutComments = null;
		for (FileType fileType : FileType.values()) {
			if (zipEntry.getName().endsWith(fileType.getValue())) {
				try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
				    byte[] buffer = new byte[16384]; // 적절한 버퍼 크기를 선택합니다.
				    int bytesRead;
				    
				    while ((bytesRead = zipInputStream.read(buffer)) != -1) {
				        baos.write(buffer, 0, bytesRead);
				    }
				    
				    byte[] contentWithoutCommentsBytes = baos.toByteArray();
				    
				    contentWithoutComments = commentRemover.removeComments(contentWithoutCommentsBytes, fileType.getValue());
				    
				} catch (IOException e) {
				    e.printStackTrace();
				}
				
// file io bount not use thread pool
				ExtractionStrategy extractionStrategy = extractionStrategyProvider.setExtractionStrategy(extractionStrategyType);
				Set<String> words = extractionStrategy.extract(contentWithoutComments);
				
				if(words.isEmpty()) {
					directory.add(zipEntry.getName());
					found=true;
					break;
				}
				
				directory.add(zipEntry.getName() + FileStatus._$INSERT);
				textContent.put(zipEntry.getName(), words);
				found=true;
				break;
			}
		}
		
		if(!found) {
			directory.add(zipEntry.getName());
		}
		
	}

    private byte[] writeSearchResultToByteArray(Map<String, Set<String>> textContent) {
        if (textContent.isEmpty()) {
            return null;
        }
        
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            mapper.writeValue(bos, textContent);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to write JSON to byte array", e);
        }
    }

	
}
