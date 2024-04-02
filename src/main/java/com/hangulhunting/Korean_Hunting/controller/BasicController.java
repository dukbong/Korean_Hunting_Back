package com.hangulhunting.Korean_Hunting.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.dto.UserInfo;
import com.hangulhunting.Korean_Hunting.dto.response.UserResDto;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.jwt.etc.TokenETC;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;
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

	@Value("${github.clientId}")
	private String clientId;
	@Value("${github.redirectUri}")
	private String redirectUri;
	@Value("${github.key}")
	private String clientSecret;

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
    public ResponseEntity<Map<String, String>> githubCallback(@RequestBody Map<String, String> payload) {
        return ResponseEntity.ok().body(gitHubService.handleGitHubCallback(payload));
    }

//	@GetMapping("/githublogin")
//	public ResponseEntity<Map<String, String>> githubLogin() {
//		String githubAuthUrl = "https://github.com/login/oauth/authorize?client_id=" + clientId;
//		return ResponseEntity.ok().body(Map.of("githubAuthUrl", githubAuthUrl));
//	}

//	@PostMapping("/githubcallback")
//	public ResponseEntity<Map<String, String>> githubcallback(@RequestBody Map<String, String> payload) {
//		log.info("callback");
//		String code = payload.get("code");
//		Map<String, String> postData = new HashMap<>();
//		postData.put("client_id", clientId);
//		postData.put("client_secret", clientSecret);
//		postData.put("code", code);
//		
//	    String jsonBody;
//	    try {
//	        jsonBody = new ObjectMapper().writeValueAsString(postData);
//	    } catch (JsonProcessingException e) {
//	        e.printStackTrace();
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//	    }
//		
//		HttpClient client = HttpClient.newHttpClient();
//		HttpRequest request;
//		User user = null;
//		try {
//			request = HttpRequest.newBuilder().uri(new URI("https://github.com/login/oauth/access_token"))
//					.header("Content-Type", "application/json")
//					.POST(HttpRequest.BodyPublishers.ofString(jsonBody))
//					.build();
//			try {
//				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//				String responseBody = response.body();
//				
//				String[] parts = responseBody.split("&");
//
//				// '='를 기준으로 각 부분을 분할하여 토큰을 추출합니다.
//				String accessToken = null;
//				for (String part : parts) {
//				    String[] keyValue = part.split("=");
//				    if (keyValue.length == 2 && keyValue[0].equals("access_token")) {
//				        accessToken = keyValue[1];
//				        break;
//				    }
//				}
//				
//				String url = "https://api.github.com/user";
//
//		        // GitHub API에 보낼 요청 생성
//		        HttpRequest userInforequest = HttpRequest.newBuilder()
//		                .uri(new URI(url))
//		                .header("Authorization", "Bearer " + accessToken) // 토큰을 헤더에 추가
//		                .build();
//
//		        // HttpClient를 사용하여 요청을 보내고 응답을 받음
//		        HttpClient userInfoclient = HttpClient.newHttpClient();
//		        HttpResponse<String> userInforesponse = userInfoclient.send(userInforequest, HttpResponse.BodyHandlers.ofString());
//		        String userInforesponseBody = userInforesponse.body();
//		        System.out.println("userInforesponseBody Body: " + userInforesponseBody);
//		        
//		        JsonNode jsonNode = new ObjectMapper().readTree(userInforesponseBody);
//		        String login = jsonNode.get("login").asText();
//		        Optional<UserEntity> findUserEntity = userRepository.findByUserId(login);
//		        String email = jsonNode.get("email").asText();
//		        String id = jsonNode.get("id").asText();
//		        user = User.builder().userId(login).userPwd(id).email(email).build();
//		        if(!findUserEntity.isPresent()) {
//		        	log.error("???");
//		        	userService.registerUser(user);
//		        }
//				
//			} catch (IOException | InterruptedException e) {
//				e.printStackTrace();
//			}
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String loginResult = authenticationService.loginProcess(user);
//		HttpHeaders header = new HttpHeaders();
//		header.add(TokenETC.AUTHORIZATION, TokenETC.PREFIX + loginResult);
//		log.info("result = {}", loginResult);
//		Map<String, String> map = new HashMap<>();
//		map.put("message", "Login Success");
//		map.put("userId", user.getUserId());
//		return ResponseEntity.ok().headers(header).body(map);
//	}

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
		UserInfo userInfo = authenticationService.userInfo();
		return ResponseEntity.ok().body(userInfo);
	}

}
