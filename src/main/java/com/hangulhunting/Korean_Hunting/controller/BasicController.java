package com.hangulhunting.Korean_Hunting.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.dto.response.UserResDto;
import com.hangulhunting.Korean_Hunting.dto.token.TokenDto;
import com.hangulhunting.Korean_Hunting.jwt.etc.TokenETC;
import com.hangulhunting.Korean_Hunting.serviceImpl.AuthenticationService;
import com.hangulhunting.Korean_Hunting.serviceImpl.RegisterUserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BasicController {

	private final RegisterUserService userService;
	private final AuthenticationService authenticationService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {
		String loginResult = authenticationService.loginProcess(user);
		HttpHeaders header = new HttpHeaders();
		header.add(TokenETC.AUTHORIZATION, TokenETC.PREFIX + loginResult);
		return ResponseEntity.ok().headers(header).body("Login Success");
	}

	@GetMapping("/logout")
	public void logout(HttpServletRequest request) {
		authenticationService.logoutProcess(request);
	}

	@PostMapping("/join")
	public ResponseEntity<UserResDto> join(@RequestBody User user) {
		UserResDto result = userService.registerUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@GetMapping("/info")
	public ResponseEntity<Object> userInfo() {
		User user = authenticationService.userInfo();
		return ResponseEntity.ok().body(user);
	}

}
