package com.hangulhunting.Korean_Hunting.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hangulhunting.Korean_Hunting.dto.PrincipalDetails;
import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.dto.UserResDto;
import com.hangulhunting.Korean_Hunting.service.AcountService;
import com.hangulhunting.Korean_Hunting.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BasicController {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUserId(), user.getUserPwd()));

			UserDetails userDetails = (PrincipalDetails) authentication.getPrincipal();

			return ResponseEntity.ok("로그인 성공 : " + userDetails.getUsername());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패 : " + user.getUserId());
		}
	}

	@PostMapping("/join")
	public ResponseEntity<UserResDto> join(@RequestBody User user) {
		UserResDto result = userService.joinProcess(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

}
