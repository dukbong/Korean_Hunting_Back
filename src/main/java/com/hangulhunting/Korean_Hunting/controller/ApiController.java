package com.hangulhunting.Korean_Hunting.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hangulhunting.Korean_Hunting.dto.ZipFile;
import com.hangulhunting.Korean_Hunting.dto.response.ApiResult;
import com.hangulhunting.Korean_Hunting.entity.enumpackage.ExtractionStrategyType;
import com.hangulhunting.Korean_Hunting.serviceImpl.FileService;
import com.hangulhunting.Korean_Hunting.serviceImpl.file.FileStructurePrinter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class ApiController {

	private final FileService fileService;
	private final FileStructurePrinter fileStructurePrinter;

	@PostMapping("/upload")
	public ApiResult apiUpload(@RequestParam("file") MultipartFile file) {
		log.info("file name = {}", file.getOriginalFilename());
		ZipFile zipFile = fileService.searchInFile(file, ExtractionStrategyType.EXTRACTION_KOREAN);
		return fileStructurePrinter.analyzeFileContent(zipFile);
	}
}
