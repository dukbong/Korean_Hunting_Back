package com.hangulhunting.Korean_Hunting.serviceImpl.file;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.stereotype.Component;

import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;

@Component
public class FileUnzipper {

	/***
	 * 주어진 InputStream으로부터 zip 파일을 압축 해제하는 서비스
	 * 
	 * @param fileInputStream zip 파일의 입력 스트림
	 * @param outputPath      압축 해제된 파일을 저장할 경로
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
	 * 주어진 입력 스트림으로부터 파일을 생성하는 서비스
	 * 
	 * @param inputStream 생성할 파일의 입력 스트림
	 * @param targetPath 생성할 파일의 경로
	 */
	public void createFile(InputStream inputStream, Path targetPath) {
		try {
			Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
