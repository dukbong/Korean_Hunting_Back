package com.hangulhunting.Korean_Hunting.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hangulhunting.Korean_Hunting.dto.PrincipalDetails;
import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.dto.UserResDto;
import com.hangulhunting.Korean_Hunting.service.AcountService;
import com.hangulhunting.Korean_Hunting.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BasicController {

	private final UserService userService;
	private final AcountService acountService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {
		PrincipalDetails detail = (PrincipalDetails) acountService.loadUserByUsername(user.getUserId());
		if(detail == null) {
			return new ResponseEntity<>("로그인에 실패하였습니다.", HttpStatus.BAD_REQUEST);
		}
		
		
		return ResponseEntity.ok("[로그인 성공] ID : " + detail.getUsername() + ", PWD : " + detail.getPassword());
	}

	@PostMapping("/join")
	public ResponseEntity<UserResDto> signup(@RequestBody User user) {
		UserResDto result = userService.joinProcess(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
}
