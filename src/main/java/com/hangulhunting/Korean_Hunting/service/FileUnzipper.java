package com.hangulhunting.Korean_Hunting.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.stereotype.Service;

import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;

@Service
public class FileUnzipper {

	/***
	 * zip 파일 압축해제 하는 서비스
	 * @param fileInputStream
	 * @param outputPath
	 */
	public void unzip(InputStream fileInputStream, Path outputPath) {
		try (ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(fileInputStream))) {
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			while (zipEntry != null) {
				Path filePath = outputPath.resolve(zipEntry.getName());
				if (!zipEntry.isDirectory()) {
					Files.createDirectories(filePath.getParent());
					createFile(zipInputStream, filePath);
				} else {
					Files.createDirectories(filePath);
				}
				zipEntry = zipInputStream.getNextEntry();
			}
		} catch (IOException e) {
			throw new CustomException(ErrorCode.FILE_UNZIP_ERROR);
		}
	}

	/***
	 * 파일 만드는 서비스 
	 * @param inputStream
	 * @param targetPath
	 */
	public void createFile(InputStream inputStream, Path targetPath) {
		try {
			Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
