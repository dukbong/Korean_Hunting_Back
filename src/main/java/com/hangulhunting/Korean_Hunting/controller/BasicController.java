package com.hangulhunting.Korean_Hunting.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.dto.response.UserResDto;
import com.hangulhunting.Korean_Hunting.dto.token.TokenDto;
import com.hangulhunting.Korean_Hunting.jwt.etc.TokenETC;
import com.hangulhunting.Korean_Hunting.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
public class BasicController {

	private final UserService userService;

	@PostMapping("/login")
	public ResponseEntity<TokenDto> login(@RequestBody User user) {
		TokenDto tokenDto = userService.loginProcess(user);
		// 헤더에 담기
		HttpHeaders header = new HttpHeaders();
		header.add(TokenETC.AUTHORIZATION, TokenETC.PREFIX + tokenDto.getAccessToken());
		return ResponseEntity.ok().headers(header).body(tokenDto);
	}

	@GetMapping("/logout")
	public void logout(HttpServletRequest request) {
		userService.logoutProcess(request);
	}

	@PostMapping("/join")
	public ResponseEntity<UserResDto> join(@RequestBody User user) {
		UserResDto result = userService.registerUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@GetMapping("/info")
	public ResponseEntity<Object> userInfo() {
		User user = userService.userInfo();
		return ResponseEntity.ok().body(user);
	}

}
