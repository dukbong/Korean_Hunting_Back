package com.hangulhunting.Korean_Hunting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hangulhunting.Korean_Hunting.dto.ZipFile;
import com.hangulhunting.Korean_Hunting.entity.enumpackage.ExtractionStrategyType;
import com.hangulhunting.Korean_Hunting.serviceImpl.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
	
	private final FileService fileService;

	@PostMapping("/upload")
	public ResponseEntity<ZipFile> fileUpload(@RequestParam("file") MultipartFile file,
											  @RequestParam("extractionStrategyType") ExtractionStrategyType extractionStrategyType) {
		ZipFile zipFile = fileService.searchInFile(file, extractionStrategyType);
		return ResponseEntity.ok().body(zipFile);
	}
}
