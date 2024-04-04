package com.hangulhunting.Korean_Hunting.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hangulhunting.Korean_Hunting.dto.GitHub;
import com.hangulhunting.Korean_Hunting.dto.ProjectBuildHistoryDto;
import com.hangulhunting.Korean_Hunting.dto.ProjectBuildHistoryInfo;
import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.dto.UserInfo;
import com.hangulhunting.Korean_Hunting.dto.response.UserResDto;
import com.hangulhunting.Korean_Hunting.jwt.etc.TokenETC;
import com.hangulhunting.Korean_Hunting.serviceImpl.AuthenticationService;
import com.hangulhunting.Korean_Hunting.serviceImpl.GitHubService;
import com.hangulhunting.Korean_Hunting.serviceImpl.RegisterUserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BasicController {

	private final RegisterUserService userService;
	private final AuthenticationService authenticationService;
	private final GitHubService gitHubService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {
		String loginResult = authenticationService.loginProcess(user);
		HttpHeaders header = new HttpHeaders();
		header.add(TokenETC.AUTHORIZATION, TokenETC.PREFIX + loginResult);
		return ResponseEntity.ok().headers(header).body("Login Success");
	}
	
    @GetMapping("/githublogin")
    public ResponseEntity<Map<String, String>> githubLogin() {
        return ResponseEntity.ok().body(gitHubService.getGitHubAuthUrl());
    }

    @PostMapping("/githubcallback")
    public ResponseEntity<GitHub> githubCallback(@RequestBody Map<String, String> payload) {
    	User githubUser = gitHubService.handleGitHubCallback(payload);
    	String loginResult = authenticationService.loginProcess(githubUser);
        HttpHeaders header = new HttpHeaders();
        header.add(TokenETC.AUTHORIZATION, TokenETC.PREFIX + loginResult);
        return ResponseEntity.ok().headers(header).body(GitHub.builder().message("GitHub Login Success").userId(githubUser.getUserId()).build());
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
	public ResponseEntity<UserInfo> userInfo() {
		UserInfo userInfo = authenticationService.userInfo();
		return ResponseEntity.ok().body(userInfo);
	}
	
	@GetMapping("/buildHistory")
	public ResponseEntity<Page<ProjectBuildHistoryDto>> buildHistory(Pageable pageable){
		Page<ProjectBuildHistoryDto> projectBuildHistoryInfo = authenticationService.buildHistory(pageable);
//		return ResponseEntity.ok().header("X-Total-Pages", String.valueOf(projectBuildHistoryInfo.getTotalPage())).body(projectBuildHistoryInfo.getProjectBuildHistorys());
		return ResponseEntity.ok().body(projectBuildHistoryInfo);
	}

}
