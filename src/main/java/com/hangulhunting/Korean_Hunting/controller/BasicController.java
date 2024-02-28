package com.hangulhunting.Korean_Hunting.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BasicController {

	private final UserRepository userRepository;

	@PostMapping("/login")
	public String login(@RequestBody User loginRequest) {
	}

	@PostMapping("/signup")
	public ResponseEntity<Object> signup(@RequestBody User user) {
		// 중복 아이디가 있다면 바로 응답
		Optional<UserEntity> findUser = userRepository.findByUserId(user.getUserId());
		if (findUser.isPresent()) return new ResponseEntity<>("아이디 중복 오류", HttpStatus.BAD_REQUEST);
		
		
		// 중복 아이디가 없을때 저장 및 응답
		UserEntity userEntity = UserEntity.builder()
										  .userId(user.getUserId())
										  .userPwd(user.getUserPwd())
										  .email(user.getEmail())
										  .company(user.getCompany())
										  .build();
		
		userRepository.save(userEntity);
		return ResponseEntity.ok().body("회원 가입에 성공하였습니다.");
	}

}
