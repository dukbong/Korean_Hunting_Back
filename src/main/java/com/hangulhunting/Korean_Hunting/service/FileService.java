package com.hangulhunting.Korean_Hunting.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hangulhunting.Korean_Hunting.dto.FileStatus;
import com.hangulhunting.Korean_Hunting.dto.FileType;
import com.hangulhunting.Korean_Hunting.dto.ZipFile;
import com.hangulhunting.Korean_Hunting.entity.NotDeleteFolder;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;
import com.hangulhunting.Korean_Hunting.repository.NotDeleteFolderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {

	private final NotDeleteFolderRepository notDeleteFolderRepository;

	public ZipFile koreanSearch(MultipartFile file) {
		UUID uid = UUID.randomUUID();
		ArrayList<String> result = new ArrayList<>();
		ZipFile zip = new ZipFile();
		Path rootFolderPath = null;
		try {
			// 프로젝트의 가장 상단에 폴더를 두고 작업할 예정 [변경 예정]
			Path currentWoringPath = Paths.get(System.getProperty("user.dir"));
			rootFolderPath = currentWoringPath.resolve("koreaHuntingFolder");
			Path uuidFolderPath = createDirectories(rootFolderPath.resolve(uid.toString()));

			// 압축해제
			unzip(file.getInputStream(), uuidFolderPath);

			result.addAll(printDirectory(uuidFolderPath, FileType.values()));
			zip.setDirectory(result);

			Path wordAddFilePath = Files.list(uuidFolderPath)
					.filter(path -> path.getFileName().toString().equals("korean_inside_JSP.txt")).findFirst()
					.orElse(null);
			if (wordAddFilePath != null) {
				byte[] fileContent = Files.readAllBytes(wordAddFilePath);
				zip.setContent(fileContent);
			}
		} catch (IOException e) {
			e.printStackTrace();
			deleteFile(rootFolderPath.resolve(uid.toString()));
		} finally {
			if (rootFolderPath != null) {
//	            deleteFile(rootFolderPath.resolve(uid.toString()));
			}
		}
		return zip;
	}

	// 압축 해제
	private void unzip(InputStream fileInputStream, Path outputPath) {
		try (ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(fileInputStream))) {
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			while (zipEntry != null) {
				Path filePath = outputPath.resolve(zipEntry.getName());
				if (!zipEntry.isDirectory()) {
					createDirectories(filePath.getParent());
					createFile(zipInputStream, filePath);
				} else {
					createDirectories(filePath);
				}
				zipEntry = zipInputStream.getNextEntry();
			}
		} catch (IOException e) {
			deleteFile(outputPath);
			throw new CustomException(ErrorCode.FILE_UNZIP_ERROR);
		}
	}

	// 파일 만들기
	private void createFile(InputStream inputStream, Path targetPath) {
		try {
			Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 폴더 만들기
	private Path createDirectories(Path path) throws IOException {
		return Files.createDirectories(path);
	}

	// 파일 구조를 반환하는 메소드
	private ArrayList<String> printDirectory(Path uuidFolderPath, FileType[] fileType) {
		ArrayList<String> fileTree = new ArrayList<>();

		try (Stream<Path> paths = Files.walk(uuidFolderPath)) {
			Path root = uuidFolderPath;
			paths.filter(Files::isRegularFile).map(path -> root.relativize(path)).map(Path::toString)
					.forEach(filePath -> processFile(uuidFolderPath, filePath, fileType, fileTree));
			Collections.reverse(fileTree);
		} catch (IOException e) {
			deleteFile(uuidFolderPath);
			throw new CustomException(ErrorCode.FILE_STRUCTURE_ERROR);
		}
		return fileTree;
	}

	// 파일 구조를 파악하는 메소드
	private void processFile(Path uuidFolderPath, String filePath, FileType[] fileType, ArrayList<String> fileTree) {
		for (FileType type : fileType) {
			if (isFileType(filePath, type)) {
				fileTree.add(appendInsertStatus(filePath, uuidFolderPath, type));
				break;
			}
		}
	}

	// 파일 path의 끝에가 FileType에 지정되어있는지 확인
	private boolean isFileType(String filePath, FileType fileType) {
		return filePath.endsWith(fileType.getValue());
	}

	// 파일 안에 한글이 있다면 FileStatus의 값을 넣어서 반환
	private String appendInsertStatus(String filePath, Path uuidFolderPath, FileType fileType) {
		String fileContent = getFileContent(uuidFolderPath, filePath);
		if (search(uuidFolderPath, filePath, fileContent, fileType.getValue())) {
			filePath += FileStatus._$INSERT;
		}
		return filePath;
	}

	// 파일을 하나씩 읽어서 문자열로 만든 후 반환하는 메소드
	public String getFileContent(Path uuidFolderPath, String targetFile) {
		StringBuilder sb = new StringBuilder();
		Path checkFilePath = uuidFolderPath.resolve(targetFile);
		// BufferedReader 보다 Files.lines가 jdk 1.8 이후 부터 효율적이라고 해서 테스트 해봤는데 큰 차이는 없었지만
		// 써보기로 결정.
		try (Stream<String> lines = Files.lines(checkFilePath)) {
			lines.forEach(line -> sb.append(line).append("\n"));
		} catch (IOException e) {
			deleteFile(uuidFolderPath);
			throw new CustomException(ErrorCode.FILE_READ_ERROR);
		}
		return sb.toString();
	}

	// [리팩토링 중...]
	// 한글이 있는지 확인하고 코드
	private boolean search(Path uuidFolderPath, String targetFile, String fileContent, String fileType) {
		Set<String> oldCharNote = new TreeSet<>();
		boolean foundKorean = false;
		Path filePath = uuidFolderPath.resolve(targetFile);
		StringBuilder sb = new StringBuilder();

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath.toString()))) {

			String removeCommentsContent = removeComments(fileContent);
			String[] arr = removeCommentsContent.split("\n");
			for (int i = 0; i < arr.length; i++) {
				String row = arr[i];
				Pattern pattern = Pattern.compile(">(.*?)<"); // 태그
				Matcher matcher = pattern.matcher(row);
				StringBuilder resultBuilder = new StringBuilder();
				while (matcher.find()) {
					String extractedText = matcher.group(1);
					if (!extractedText.matches(".*\\$\\{.*?}.*")) {
						resultBuilder.append(extractedText);
						resultBuilder.append(" "); // Add space
					}
				}
				String extractedText = resultBuilder.toString().trim();
				if (containsKorean(extractedText)) {
					oldCharNote.add(extractedText);
					foundKorean = true;
				}
				sb.append(arr[i]).append("\n");
			}
			bw.write(sb.toString());
		} catch (IOException e) {
			deleteFile(uuidFolderPath);
			throw new CustomException(ErrorCode.FILE_SEARCH_ERROR);
		}

		if(oldCharNote.size() <= 1) {
			String middleStr = System.getProperty("os.name").contains("Window") ? "\\" : "/";
			try (BufferedWriter bw = new BufferedWriter(
					new FileWriter(uuidFolderPath + middleStr + "korean_inside_JSP.txt", true));) {
				
				List<String> oldCharList = new ArrayList<>(oldCharNote);
				for (int i = 0; i < oldCharList.size(); i++) {
					if (i == 0) {
						bw.write(targetFile + "\n");
					}
					String row = (i + 1) + ". " + oldCharList.get(i) + "\n";
					bw.write(row);
				}
				
			} catch (IOException e) {
				deleteFile(uuidFolderPath);
				throw new CustomException(ErrorCode.FILE_WRITE_ERROR);
			}
		}
		
		return foundKorean;
	}

	// 주석 제거 메소드
	private String removeComments(String fileContent) {
		// 여러줄 주석 제거
		fileContent = fileContent.replaceAll("/\\*(.|[\\r\\n])*?\\*/", "");

		// HTML 주석 제거
		fileContent = fileContent.replaceAll("<!--(.*?)-->", "");

		// JSP 주석 제거
		fileContent = fileContent.replaceAll("<%--(.*?)--%>", "");

		// 한 줄 주석 제거
		fileContent = fileContent.replaceAll("//.*", "");
		fileContent = fileContent.replaceAll("<%.*%>", "");
		fileContent = fileContent.replaceAll("<%.*%>", "");

		return fileContent;
	}

	private boolean containsKorean(String text) {
		return text != null && text.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*");
	}

	private void deleteFile(Path deleteFilePath) {
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
