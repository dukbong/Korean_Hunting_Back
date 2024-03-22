package com.hangulhunting.Korean_Hunting.serviceImpl;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

import org.springframework.stereotype.Service;

import com.hangulhunting.Korean_Hunting.entity.NotDeleteFolder;
import com.hangulhunting.Korean_Hunting.repository.NotDeleteFolderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileDeleter {

	private final NotDeleteFolderRepository notDeleteFolderRepository;

	/***
	 * 지정된 경로의 파일을 삭제하는 서비스
	 * 
	 * @param deleteFilePath 삭제할 파일 또는 디렉토리의 경로
	 */
	public void deleteFile(Path deleteFilePath) {
		try {
			Files.walkFileTree(deleteFilePath, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE,
					new SimpleFileVisitor<Path>() {
						@Override
						public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
							Files.delete(file);
							return FileVisitResult.CONTINUE;
						}

						@Override
						public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
							// 예외 발생 시 계속 진행
							return FileVisitResult.CONTINUE;
						}

						@Override
						public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
							Files.delete(dir);
							return FileVisitResult.CONTINUE;
						}
					});
		} catch (IOException e) {
			handleDeletionError(deleteFilePath);
			e.printStackTrace();
		}
	}

	/***
	 * 파일 또는 디렉토리 삭제 중 발생한 오류를 처리하는 서비스
	 * 
	 * @param deleteFilePath 삭제 중 오류가 발생한 파일 또는 디렉토리의 경로
	 */
	private void handleDeletionError(Path deleteFilePath) {
		NotDeleteFolder notDeleteFolder = NotDeleteFolder.builder().folderPath(deleteFilePath.toString()).build();
		notDeleteFolderRepository.save(notDeleteFolder);
	}
}
