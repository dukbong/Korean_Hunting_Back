package com.hangulhunting.Korean_Hunting.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hangulhunting.Korean_Hunting.dto.response.ApiIssuance;
import com.hangulhunting.Korean_Hunting.entity.ApiTokenEntity;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;
import com.hangulhunting.Korean_Hunting.jwt.TokenProvider;
import com.hangulhunting.Korean_Hunting.repository.ApiTokenRepository;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ApiCreateController {

	private final TokenProvider tokenProvider;
	private final UserRepository userRepository;
	private final ApiTokenRepository apiTokenRepository;
	
	@GetMapping("/crateApi")
	public ResponseEntity<ApiIssuance> apiCreate(@RequestParam("userId") String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND_BY_ID));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ApiIssuance apiTokenInfo = tokenProvider.apigenerateToken(authentication);
		ApiTokenEntity apiTokenEntity = ApiTokenEntity.builder()
													  .apiToken(apiTokenInfo.getApiToken())
													  .userEntity(userEntity)
													  .issuanceTime(LocalDate.now())
													  .tokenExpiresIn(apiTokenInfo.getTokenExpiresIn())
													  .build();
		apiTokenRepository.save(apiTokenEntity);
		
		return ResponseEntity.ok().body(apiTokenInfo);
	}
	
}
