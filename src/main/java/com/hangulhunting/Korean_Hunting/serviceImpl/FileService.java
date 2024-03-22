package com.hangulhunting.Korean_Hunting.serviceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hangulhunting.Korean_Hunting.dto.ZipFile;
import com.hangulhunting.Korean_Hunting.entity.enumpackage.ExtractionStrategyType;
import com.hangulhunting.Korean_Hunting.entity.enumpackage.FileConstants;
import com.hangulhunting.Korean_Hunting.entity.enumpackage.FileType;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;
import com.hangulhunting.Korean_Hunting.service.ExtractionStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {
	
	private final FileDeleter fileDeleter;
	private final FileUnzipper fileUnzipper;
	private final FileStructurePrinter fileStructurePrinter;
	/***
	 * 사용자가 원하는 파일에서 원하는 내용을 찾아주는 서비스
	 * 
	 * @param file 사용자가 제공한 파일
	 * @param extractionStrategyType 파일에 적용할 전략
	 * @return 사용자가 원하는 내용이 포함된 파일 구조 및 텍스트
	 */
	public ZipFile searchInFile(MultipartFile file, ExtractionStrategyType extractionStrategyType) {
		UUID uid = UUID.randomUUID();
		Path rootFolderPath = null;

		try {
			rootFolderPath = createRootFolder();
			Path tempFolderPath = Files.createDirectories(rootFolderPath.resolve(uid.toString()));
			ZipFile zipFile = processFileStructure(file, tempFolderPath, extractionStrategyType);

			return zipFile;
		} catch (IOException e) {
			e.printStackTrace();
			throw new CustomException(ErrorCode.FILE_PROCESSING_ERROR);
		} finally {
			deleteRootFolder(rootFolderPath, uid);
		}
	}

	/***
	 * 루트 폴더를 생성하는 서비스
	 * 
	 * @return 생성된 루트 폴더의 경로
	 * @throws IOException 폴더 생성 중 오류 발생 시
	 */
	private Path createRootFolder() throws IOException {
		Path currentWorkingPath = Paths.get(System.getProperty("user.dir"));
		return currentWorkingPath.resolve(FileConstants.SERVICE_FILE_NAME.getValue());
	}

	/***
	 * 파일 구조를 처리하고 사용자가 원하는 내용을 찾는 서비스
	 * 
	 * @param file 사용자가 제공한 파일
	 * @param tempFolderPath 파일 처리를 위한 임시 폴더 경로
	 * @param extractionStrategyType 파일에 적용할 전략
	 * @return 사용자가 원하는 내용이 포함된 파일 구조 및 텍스트
	 * @throws IOException 파일 처리 중 오류 발생 시
	 */
	private ZipFile processFileStructure(MultipartFile file, Path tempFolderPath, ExtractionStrategyType extractionStrategyType) throws IOException {
		ZipFile zipFile = new ZipFile();
		fileUnzipper.unzip(file.getInputStream(), tempFolderPath);
		ArrayList<String> fileStructure = fileStructurePrinter.printDirectory(tempFolderPath, FileType.values(), extractionStrategyType);
		zipFile.setDirectory(fileStructure);
		Path wordAddFilePath = Files.list(tempFolderPath)
									.filter(path -> path.getFileName().toString().equals(FileConstants.SERVICE_TEXT_FILE_NAME.getValue()))
									.findFirst()
									.orElse(null);
		if (wordAddFilePath != null) {
			byte[] fileContent = Files.readAllBytes(wordAddFilePath);
			zipFile.setContent(fileContent);
		}
		return zipFile;
	}

	/***
	 * 생성된 루트 폴더를 삭제하는 서비스
	 * 
	 * @param rootFolderPath 루트 폴더의 경로
	 * @param folderUid 폴더의 고유 ID
	 */
	private void deleteRootFolder(Path rootFolderPath, UUID folderUid) {
		if (rootFolderPath != null) {
			try {
				fileDeleter.deleteFile(rootFolderPath.resolve(folderUid.toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
