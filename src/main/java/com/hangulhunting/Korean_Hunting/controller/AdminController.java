package com.hangulhunting.Korean_Hunting.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.dto.response.UserResDto;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.serviceImpl.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;
	
	@GetMapping("/get/users")
	public ResponseEntity<UserResDto> users(){
		List<UserEntity> list = adminService.users();
		List<User> users = new ArrayList<>();
		for(UserEntity user : list) {
			users.add(User.builder()
						  .userId(user.getUserId())
						  .email(user.getEmail())
						  .company(user.getEmail())
						  .build());
		}
		return ResponseEntity.ok().body(new UserResDto(users));
	}
	
}
