package com.hangulhunting.Korean_Hunting.service;

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
	 * Path에 있는 걸 삭제하는 서비스
	 * - 매개변수로는 uuidFolderPath를 넣어주면 된다.
	 * @param deleteFilePath
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
			NotDeleteFolder notDeleteFolder = NotDeleteFolder.builder().folderPath(deleteFilePath.toString()).build();
			notDeleteFolderRepository.save(notDeleteFolder);
			e.printStackTrace();
		}
	}
}
