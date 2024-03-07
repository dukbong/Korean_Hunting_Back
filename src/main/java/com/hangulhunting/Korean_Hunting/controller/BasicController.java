package com.hangulhunting.Korean_Hunting.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hangulhunting.Korean_Hunting.dto.PrincipalDetails;
import com.hangulhunting.Korean_Hunting.dto.TokenDto;
import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.dto.UserResDto;
import com.hangulhunting.Korean_Hunting.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BasicController {

	private final UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<TokenDto> login(@RequestBody User user) {
		return ResponseEntity.ok().body(userService.loginProcess(user));
	}

	@PostMapping("/join")
	public ResponseEntity<UserResDto> join(@RequestBody User user) {
		UserResDto result = userService.joinProcess(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	@GetMapping("/info/{id}")
	public ResponseEntity<Object> userInfo(@PathVariable String userId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
		
		if(userId.equals(userDetails.getUsername())) {
			return ResponseEntity.ok().body(userService.userInfo(userId));
		}
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new UserResDto("읽을 권한이 없습니다."));
	}
	
}
