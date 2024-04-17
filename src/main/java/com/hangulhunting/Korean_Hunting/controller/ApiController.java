package com.hangulhunting.Korean_Hunting.controller;

import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hangulhunting.Korean_Hunting.dto.ZipFile;
import com.hangulhunting.Korean_Hunting.dto.response.ApiResult;
import com.hangulhunting.Korean_Hunting.entity.ProjectBuildHistory;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.entity.enumpackage.ExtractionStrategyType;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;
import com.hangulhunting.Korean_Hunting.serviceImpl.FileService;
import com.hangulhunting.Korean_Hunting.serviceImpl.ProjectBuildHistoryService;
import com.hangulhunting.Korean_Hunting.serviceImpl.file.AnalyzeFileContent;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiController {

	private final FileService fileService;
	private final ProjectBuildHistoryService projectBuildHistoryService; 
	private final AnalyzeFileContent analyzeFileContent;
	private final UserRepository userRepository;

	@PostMapping("/upload")
	public ApiResult apiUpload(@RequestParam("file") MultipartFile file, @RequestParam("projectName") String projectName) {
		ZipFile zipFile = null;
		UserEntity findUser = null;
		ProjectBuildHistory projectBuild = null;
		try {
			zipFile = fileService.searchInFile(file, ExtractionStrategyType.EXTRACTION_KOREAN);
			String username = getUsernameFromSecurityContext();
			findUser = userRepository.findByUserId(username)
									 .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND_BY_ID));
			projectBuild = ProjectBuildHistory.builder()
											  .projectName(projectName)
											  .buildTime(LocalDateTime.now())
											  .status(true)
											  .userEntity(findUser)
											  .build();
		} catch (Exception e) {
			projectBuild = ProjectBuildHistory.builder()
											  .projectName(projectName)
											  .buildTime(LocalDateTime.now())
											  .status(false)
											  .userEntity(findUser)
											  .build();
		} finally {
			projectBuildHistoryService.projectBuild(projectBuild);
			findUser.addProjectBuildHistory(projectBuild);
		}
		return analyzeFileContent.analyzeFileContent(zipFile);
	}

    private String getUsernameFromSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        return null;
    }
	
}
