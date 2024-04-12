package com.hangulhunting.Korean_Hunting.serviceImpl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
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
	
    @Async
    public CompletableFuture<ZipFile> searchInFileAsync(MultipartFile file, ExtractionStrategyType extractionStrategyType) {
        return CompletableFuture.supplyAsync(() -> {
            return searchInFile(file, extractionStrategyType);
        });
    }

    public ZipFile searchInFile(MultipartFile file, ExtractionStrategyType extractionStrategyType) {
        Map<String, Set<String>> textContent = new ConcurrentHashMap<>();
        ZipFile zipFile = new ZipFile();
        List<String> directory = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
        	 BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, /*1048576*/ 65536);
        	 ArchiveInputStream<? extends ArchiveEntry> ais = new ArchiveStreamFactory().createArchiveInputStream(ArchiveStreamFactory.ZIP, bufferedInputStream)) {

            ArchiveEntry entry;
            while ((entry = ais.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                	processZipEntry(ais, entry, directory, textContent, extractionStrategyType);
                }
            }

            zipFile.setContent(writeSearchResultToByteArray(textContent));
            zipFile.setDirectory(directory);
            return zipFile;

        } catch (IOException | ArchiveException e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.FILE_UNZIP_ERROR);
        } 
    }
	
	private void processZipEntry(ArchiveInputStream<? extends ArchiveEntry> ais, ArchiveEntry zipEntry, List<String> directory, Map<String, Set<String>> textContent, ExtractionStrategyType extractionStrategyType)
			 {
		boolean found = false;
		String contentWithoutComments = null;
		for (FileType fileType : FileType.values()) {
			if (zipEntry.getName().endsWith(fileType.getValue())) {
				
				try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
					byte[] buffer = new byte[65536]; // 적절한 버퍼 크기를 선택합니다.
		            int bytesRead;
		            StringBuilder contentWithoutCommentsBuilder = new StringBuilder();
		            while ((bytesRead = ais.read(buffer)) != -1) {
//	                    baos.write(buffer, 0, bytesRead);
		            	contentWithoutCommentsBuilder.append(new String(buffer, 0, bytesRead));
	                    
	                }
//		            contentWithoutComments = commentRemover
//		            		.removeComments(new ByteArrayInputStream(baos.toByteArray())/* , new byte[2048] */, /* new byte[4096], */ fileType.getValue());
		            contentWithoutComments = commentRemover.removeComments(
		                    new ByteArrayInputStream(contentWithoutCommentsBuilder.toString().getBytes()),
		                    fileType.getValue());
				} catch(IOException e) {
					e.printStackTrace();
				}
				
				// 2~8kb까지만 효과가 있음.
				ExtractionStrategy extractionStrategy = extractionStrategyProvider
						.setExtractionStrategy(extractionStrategyType);
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
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(bos, textContent);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to write JSON to byte array", e);
        }
    }

	
}
