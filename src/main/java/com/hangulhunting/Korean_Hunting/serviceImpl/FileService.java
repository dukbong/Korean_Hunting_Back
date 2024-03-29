package com.hangulhunting.Korean_Hunting.serviceImpl;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hangulhunting.Korean_Hunting.dto.ZipFile;
import com.hangulhunting.Korean_Hunting.entity.enumpackage.ExtractionStrategyType;
import com.hangulhunting.Korean_Hunting.entity.enumpackage.FileConstants;
import com.hangulhunting.Korean_Hunting.entity.enumpackage.FileStatus;
import com.hangulhunting.Korean_Hunting.entity.enumpackage.FileType;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;
import com.hangulhunting.Korean_Hunting.service.ExtractionStrategy;
import com.hangulhunting.Korean_Hunting.serviceImpl.file.CommentRemover;
import com.hangulhunting.Korean_Hunting.serviceImpl.file.ExtractionStrategyProvider;
import com.hangulhunting.Korean_Hunting.serviceImpl.file.FileDeleter;
import com.hangulhunting.Korean_Hunting.serviceImpl.file.FileStructurePrinter;
import com.hangulhunting.Korean_Hunting.serviceImpl.file.FileUnzipper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

	private final FileDeleter fileDeleter;
	private final FileUnzipper fileUnzipper;
	private final FileStructurePrinter fileStructurePrinter;
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
	 * @param file                   사용자가 제공한 파일
	 * @param tempFolderPath         파일 처리를 위한 임시 폴더 경로
	 * @param extractionStrategyType 파일에 적용할 전략
	 * @return 사용자가 원하는 내용이 포함된 파일 구조 및 텍스트
	 * @throws IOException 파일 처리 중 오류 발생 시
	 */
	private ZipFile processFileStructure(MultipartFile file, Path tempFolderPath,
			ExtractionStrategyType extractionStrategyType) throws IOException {
		ZipFile zipFile = new ZipFile();
//		1. 기존 방식 : 실제 저장 해서 탐색 
//		fileUnzipper.unzip(file.getInputStream(), tempFolderPath);
//		ArrayList<String> fileStructure = fileStructurePrinter.printDirectory(tempFolderPath, FileType.values(), extractionStrategyType);
//		zipFile.setDirectory(fileStructure);
//		Path wordAddFilePath = Files.list(tempFolderPath)
//									.filter(path -> path.getFileName().toString().equals(FileConstants.SERVICE_TEXT_FILE_NAME.getValue()))
//									.findFirst()
//									.orElse(null);
//		if (wordAddFilePath != null) {
//			byte[] fileContent = Files.readAllBytes(wordAddFilePath);
//			zipFile.setContent(fileContent);
//		}
//		return zipFile;

//		2. 변경 방식 : 메모리에서 처리
		ArrayList<String> directory = new ArrayList<>();
		try (ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(file.getInputStream()))) {
			ZipEntry zipEntry;
			byte[] buffer = new byte[1024];
			int len;
			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				// zipEntry => pages/com/popup/UploadFileP.jsp
				if (!zipEntry.isDirectory()) {
					for (FileType fileType : FileType.values()) {
						if (zipEntry.getName().endsWith(fileType.getValue())) {
							StringBuilder contentBuilder = new StringBuilder();
							try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
								while ((len = zipInputStream.read(buffer)) > 0) {
									baos.write(buffer, 0, len);
								}
								// 파일 내용 처리
								contentBuilder.append(new String(baos.toByteArray(), StandardCharsets.UTF_8));
							}
							String content = contentBuilder.toString();
							String contentWithoutComments = commentRemover.removeComments(content, fileType.getValue());
							ExtractionStrategy extractionStrategy = extractionStrategyProvider
									.setExtractionStrategy(extractionStrategyType);
							Set<String> words = extractionStrategy.extract(contentWithoutComments);
							if (search(zipEntry.getName(), words, zipFile)) {
								String newFilePath = zipEntry.getName() + FileStatus._$INSERT;
								directory.add(newFilePath);
							} else {
								directory.add(zipEntry.getName());
							}
						}
					}
				}
			}
		} catch (IOException e) {
			throw new CustomException(ErrorCode.FILE_UNZIP_ERROR);
		}

		zipFile.setDirectory(directory);
		return zipFile;
	}

	private boolean search(String filePath, Set<String> words, ZipFile zipFile) {
		if (!words.isEmpty()) {
			if(zipFile.getContent() != null) {
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
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    try {
	        bos.write(filePath.getBytes());
	        bos.write(System.lineSeparator().getBytes());
	        
	        int count = 1;
	        for (String word : words) {
	            String line = count + ". " + word;
	            bos.write(line.getBytes());
	            bos.write(System.lineSeparator().getBytes());
	            count++;
	        }
	        return bos.toByteArray();
	    } catch (IOException e) {
	        e.printStackTrace();
	        return new byte[0];
	    } finally {
	        try {
	            bos.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}


	/***
	 * 생성된 루트 폴더를 삭제하는 서비스
	 * 
	 * @param rootFolderPath 루트 폴더의 경로
	 * @param folderUid      폴더의 고유 ID
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
