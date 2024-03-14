package com.hangulhunting.Korean_Hunting.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
public class FileService {

	private final NotDeleteFolderRepository notDeleteFolderRepository; 
	
	public ZipFile koreanSearch(MultipartFile file) {
		UUID uid = UUID.randomUUID();
		ArrayList<String> result = new ArrayList<>();
		ZipFile zip = new ZipFile();
		Path tempDirectory = null;
		try {
			String currentWorkingDirectory = System.getProperty("user.dir");
			Path projectRoot = Paths.get(currentWorkingDirectory); // 절대 경로로 표시
			tempDirectory = Files.createDirectories(projectRoot.resolve("temp-zip")); //
			Path uuidDirectory = Files.createDirectories(tempDirectory.resolve(uid.toString()));
			Path uploadedFilePath = uuidDirectory.resolve(file.getOriginalFilename());
			Files.copy(file.getInputStream(), uploadedFilePath, StandardCopyOption.REPLACE_EXISTING);
			String unzipDirectory = unzip(uploadedFilePath.toString(), uuidDirectory.toString());
			result.addAll(printDirectory(unzipDirectory, uuidDirectory.toString(), FileType.values()));
			zip.setDirectory(result);

			Path wordAddFilePath = Files.list(uuidDirectory)
					.filter(path -> path.getFileName().toString().equals("korean_inside_JSP.txt")).findFirst().orElse(null);
			if (wordAddFilePath != null) {
				byte[] fileContent = Files.readAllBytes(wordAddFilePath);
				zip.setContent(fileContent);
			}
		} catch (IOException e) {
			e.printStackTrace();
			deleteFile(tempDirectory.resolve(uid.toString()));
		} finally {
			if (tempDirectory != null) {
	            deleteFile(tempDirectory.resolve(uid.toString()));
	        }
		}
		return zip;
	}

	private String unzip(String zipFilePath, String destDirectory) {
		String unzipDirectory = null;
		try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(Paths.get(zipFilePath)))) {
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			unzipDirectory = Paths.get(destDirectory, zipEntry.getName()).toString();
			while (zipEntry != null) {
				Path filePath = Paths.get(destDirectory, zipEntry.getName());
				if (!zipEntry.isDirectory()) {
					Files.createDirectories(filePath.getParent());
					Files.copy(zipInputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				} else {
					Files.createDirectories(filePath);
				}
				zipEntry = zipInputStream.getNextEntry();
			}
		} catch (IOException e) {
			deleteFile(Paths.get(destDirectory));
			throw new CustomException(ErrorCode.FILE_UNZIP_ERROR);
		}
		return unzipDirectory;
	}

	private ArrayList<String> printDirectory(String directoryPath, String tempDirectory, FileType[] fileType) {
		
		ArrayList<String> list = new ArrayList<>(); // 확인용
		try (Stream<Path> paths = Files.walk(Paths.get(tempDirectory))) {
			Path root = Paths.get(tempDirectory);
			paths.filter(Files::isRegularFile).map(path -> root.relativize(path)).map(Path::toString).forEach(i -> {
				for (int j = 0; j < fileType.length; j++) {
					if (i.endsWith(fileType[j].getValue())) {
						String fileContent = FileContent(tempDirectory, i);
						boolean check = search(tempDirectory, i, fileContent, fileType[j].getValue());
						if (check) {
							i += FileStatus._$INSERT;
						}
						break;
					}
				}
				list.add(i);
			});
			Collections.reverse(list);
		} catch (IOException e) {
			deleteFile(Paths.get(tempDirectory));
			throw new CustomException(ErrorCode.FILE_STRUCTURE_ERROR);
		}

		return list;
	}


	public String FileContent(String tempDirectory, String targetFile) {
		StringBuilder sb = new StringBuilder();
		sb.append(tempDirectory).append("/").append("/").append(targetFile);
		String filePath = sb.toString();
		sb.setLength(0);

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (IOException e) {
			deleteFile(Paths.get(tempDirectory));
			throw new CustomException(ErrorCode.FILE_READ_ERROR);
		}
		return sb.toString();
	}

	private boolean search(String tempDirectory, String targetFile, String fileContent,
			String fileType) {
		StringBuilder sb = new StringBuilder();
		boolean foundKorean = false;
		sb.append(tempDirectory).append("/").append("/").append(targetFile);
		String filePath = sb.toString();

		Set<String> oldCharNote = new TreeSet<>();
		sb.setLength(0); // 초기화

		try (FileWriter fileWriter = new FileWriter(filePath); BufferedWriter bw = new BufferedWriter(fileWriter)) {
			String[] arr = fileContent.split("\n");
			if (targetFile.endsWith(fileType)) {
				for (int i = 0; i < arr.length; i++) {
					String row = arr[i];
					row = row.replaceAll("<!--(.*?)-->", "");
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
			}
			bw.write(sb.toString());
		} catch (IOException e) {
			deleteFile(Paths.get(tempDirectory));
			throw new CustomException(ErrorCode.FILE_SEARCH_ERROR);
		}
		
		String middleStr = System.getProperty("os.name").contains("Window") ? "\\" : "/";
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempDirectory + middleStr + "korean_inside_JSP.txt", true));) {
			
			List<String> oldCharList = new ArrayList<>(oldCharNote);
			for(int i  = 0; i < oldCharList.size(); i++) {
				if(i == 0) {
					bw.write(targetFile + "\n");
				}
				String row = (i + 1) + ". " + oldCharList.get(i) + "\n";
				bw.write(row);
			}
			
		} catch (IOException e) {
			deleteFile(Paths.get(tempDirectory));
			throw new CustomException(ErrorCode.FILE_WRITE_ERROR);
		}
		return foundKorean;
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
			NotDeleteFolder notDeleteFolder = NotDeleteFolder.builder()
															 .folderPath(deleteFilePath.toString())
															 .build();
			notDeleteFolderRepository.save(notDeleteFolder);
			e.printStackTrace();
		}
	}
}
