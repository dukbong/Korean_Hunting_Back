package com.hangulhunting.Korean_Hunting.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.dto.UserRole;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;
import com.hangulhunting.Korean_Hunting.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BasicController {

	private final UserService userService;
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping("/login")
	public void login(@RequestBody User user) {
		
	}

	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody User user) {
		if(userService.userIdCheck(user.getUserId())) {
			return new ResponseEntity<>("회원가입하려는 ID가 중복되었습니다.", HttpStatus.BAD_REQUEST);
		}
		
		UserEntity userEntity = UserEntity.builder()
										  .userId(user.getUserId())
										  .userPwd(bCryptPasswordEncoder.encode(user.getUserPwd()))
										  .email(user.getEmail())
										  .company(user.getCompany())
										  .role(UserRole.USER)
										  .build();
		
		userRepository.save(userEntity);
		return ResponseEntity.ok().body("회원 가입에 성공하였습니다.");
	}

}
