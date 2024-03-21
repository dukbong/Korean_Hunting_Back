package com.hangulhunting.Korean_Hunting.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hangulhunting.Korean_Hunting.dto.FileType;
import com.hangulhunting.Korean_Hunting.dto.ZipFile;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {
	
	private final FileDeleter fileDeleter;
	private final FileUnzipper fileUnzipper;
	private final FileStructurePrinter fileStructurePrinter;

	public ZipFile koreanSearch(MultipartFile file) {
		UUID uid = UUID.randomUUID();
		Path rootFolderPath = null;

		try {
			rootFolderPath = createRootFolder();
			Path uuidFolderPath = Files.createDirectories(rootFolderPath.resolve(uid.toString()));

			ZipFile zip = processFileStructure(file, uuidFolderPath);

			return zip;
		} catch (IOException e) {
			e.printStackTrace();
			throw new CustomException(ErrorCode.FILE_PROCESSING_ERROR);
		} finally {
			deleteRootFolder(rootFolderPath, uid);
		}
	}

	private Path createRootFolder() throws IOException {
		Path currentWorkingPath = Paths.get(System.getProperty("user.dir"));
		return currentWorkingPath.resolve("koreaHuntingFolder");
	}

	private ZipFile processFileStructure(MultipartFile file, Path uuidFolderPath) throws IOException {
		ZipFile zip = new ZipFile();
		fileUnzipper.unzip(file.getInputStream(), uuidFolderPath);
		ArrayList<String> result = fileStructurePrinter.printDirectory(uuidFolderPath, FileType.values());
		zip.setDirectory(result);
		Path wordAddFilePath = Files.list(uuidFolderPath)
									.filter(path -> path.getFileName().toString().equals("text_package.txt"))
									.findFirst()
									.orElse(null);
		if (wordAddFilePath != null) {
			byte[] fileContent = Files.readAllBytes(wordAddFilePath);
			zip.setContent(fileContent);
		}
		return zip;
	}

	private void deleteRootFolder(Path rootFolderPath, UUID uid) {
		if (rootFolderPath != null) {
			try {
				fileDeleter.deleteFile(rootFolderPath.resolve(uid.toString()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
