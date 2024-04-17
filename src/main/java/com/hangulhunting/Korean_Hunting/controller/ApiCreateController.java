package com.hangulhunting.Korean_Hunting.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hangulhunting.Korean_Hunting.dto.response.ApiIssuance;
import com.hangulhunting.Korean_Hunting.dto.response.UserResDto;
import com.hangulhunting.Korean_Hunting.entity.ApiTokenEntity;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;
import com.hangulhunting.Korean_Hunting.jwt.TokenProvider;
import com.hangulhunting.Korean_Hunting.repository.ApiTokenRepository;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ApiCreateController {

	private final TokenProvider tokenProvider;
	private final UserRepository userRepository;
	private final ApiTokenRepository apiTokenRepository;
	private final ResourceLoader resourceLoader;
	
	@Value("${file.name}")
	private String fileName;

	@GetMapping("/createApi")
	public ResponseEntity<ApiIssuance> createApiToken(@RequestParam("userId") String userId) {
		UserEntity userEntity = getUserById(userId);
		checkAndDeleteExpiredToken(userEntity);
		ApiIssuance apiIssuance = generateAndSaveToken(userEntity);
		return ResponseEntity.ok().body(apiIssuance);
	}
	
	@GetMapping("/getfile")
	public ResponseEntity<UserResDto> getFile(){
        byte[] data;
        try {
        	String filePath = "classpath:/static/file/" + fileName;
        	Resource resource = resourceLoader.getResource(filePath);
            data = Files.readAllBytes(Paths.get(resource.getURI()));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
        
        return ResponseEntity.ok().body(new UserResDto(data));
    }

	private UserEntity getUserById(String userId) {
		return userRepository.findByUserId(userId)
				.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND_BY_ID));
	}

	private void checkAndDeleteExpiredToken(UserEntity userEntity) {
		Optional<ApiTokenEntity> apiTokenOpt = apiTokenRepository.findByUserEntity(userEntity);
		if (apiTokenOpt.isPresent() && !tokenProvider.validateToken(apiTokenOpt.get().getApiToken())) {
			try {
				apiTokenRepository.deleteByUserEntity(userEntity);
			} catch (Exception e) {
				throw new CustomException(ErrorCode.API_CREATE_ERROR);
			}
		}
	}

	private ApiIssuance generateAndSaveToken(UserEntity userEntity) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ApiIssuance apiIssuance = tokenProvider.apigenerateToken(authentication);
		ApiTokenEntity apiTokenEntity = ApiTokenEntity.builder()
													  .apiToken(apiIssuance.getApiToken())
													  .userEntity(userEntity)
													  .issuanceTime(LocalDate.now())
													  .tokenExpiresIn(apiIssuance.getTokenExpiresIn())
													  .build();
		apiTokenRepository.save(apiTokenEntity);
		return apiIssuance;
	}
}