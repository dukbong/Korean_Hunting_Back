package com.hangulhunting.Korean_Hunting.controller.etc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileReadPerformanceTest {

	@BeforeEach
	public void dummyData() {
		String directoryPath = "src/test/java/com/hangulhunting/Korean_Hunting/etc/dummy";
		String filePath = directoryPath + "/dummy.txt";

		// 폴더가 존재하지 않으면 생성합니다.
		if (!Files.exists(Paths.get(directoryPath))) {
			try {
				Files.createDirectories(Paths.get(directoryPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 파일이 존재하지 않으면 생성합니다.
		if (!Files.exists(Paths.get(filePath))) {
			long fileSizeInBytes = 1024 * 1024 * 500; // 500MB

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
				long bytesWritten = 0;
				while (bytesWritten < fileSizeInBytes) {
					// 10MB씩 반복해서 데이터를 씁니다.
					String data = generateDummyData(10 * 1024 * 1024); // 10MB
					writer.write(data);
					bytesWritten += data.length();
				}
//	            log.info("더미 대용량 파일이 생성되었습니다.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String generateDummyData(int dataSize) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < dataSize; i++) {
			sb.append((char) ((int) (Math.random() * 94) + 32));
		}
		return sb.toString();
	}

	@Test
	public void FileReadSpeed() {

		String testFilePath = "src/test/java/com/hangulhunting/Korean_Hunting/etc/dummy/dummy.txt";

		long start, end;
		StringBuilder sb = new StringBuilder();

		start = System.currentTimeMillis();
		try (BufferedReader br = new BufferedReader(new FileReader(testFilePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		end = System.currentTimeMillis();

		log.info("BufferdReader File Read Time = {}ns", (end - start));

		Long bufferedReaderTime = end - start;

		sb.setLength(0);

		start = System.currentTimeMillis();
		try (Stream<String> stream = Files.lines(Paths.get(testFilePath))) {
			stream.forEach(line -> {
				sb.append(line).append("\n");
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		end = System.currentTimeMillis();

		log.info("File Lines File Read Time = {}ns", (end - start));

		Long filesLinesTime = end - start;

		sb.setLength(0);

		Assertions.assertThat(filesLinesTime).isLessThan(bufferedReaderTime);
	}
}
