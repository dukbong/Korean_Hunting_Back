package com.hangulhunting.Korean_Hunting.serviceImpl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.jwt.etc.TokenETC;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GitHubService {

    private final UserRepository userRepository;
    private final RegisterUserService userService;
    private final AuthenticationService authenticationService;

    @Value("${github.clientId}")
    private String clientId;

    @Value("${github.clientSecret}")
    private String clientSecret;

    public Map<String, String> getGitHubAuthUrl() {
        String githubAuthUrl = "https://github.com/login/oauth/authorize?client_id=" + clientId;
        return Collections.singletonMap("githubAuthUrl", githubAuthUrl);
    }

    public Map<String, String> handleGitHubCallback(Map<String, String> payload) {
        String code = payload.get("code");
        String accessToken = getAccessToken(code);
        String userInfoResponseBody = getUserInfo(accessToken);
        return processUserInfo(userInfoResponseBody);
    }

    private String getAccessToken(String code) {
        Map<String, String> postData = Map.of(
                "client_id", clientId,
                "client_secret", clientSecret,
                "code", code
        );

        try {
            String jsonBody = new ObjectMapper().writeValueAsString(postData);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://github.com/login/oauth/access_token"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            return extractAccessToken(responseBody);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String extractAccessToken(String responseBody) {
        String[] parts = responseBody.split("&");
        for (String part : parts) {
            String[] keyValue = part.split("=");
            if (keyValue.length == 2 && keyValue[0].equals("access_token")) {
                return keyValue[1];
            }
        }
        return null;
    }

    private String getUserInfo(String accessToken) {
        String url = "https://api.github.com/user";
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Authorization", "Bearer " + accessToken)
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Map<String, String> processUserInfo(String userInfoResponseBody) {
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(userInfoResponseBody);
            String login = jsonNode.get("login").asText();
            Optional<UserEntity> findUserEntity = userRepository.findByUserId(login);
            String email = jsonNode.get("email").asText();
            String id = jsonNode.get("id").asText();
            User user = User.builder().userId(login).userPwd(id).email(email).build();
            if (!findUserEntity.isPresent()) {
                userService.registerUser(user);
            }
            String loginResult = authenticationService.loginProcess(user);
            HttpHeaders header = new HttpHeaders();
            header.add(TokenETC.AUTHORIZATION, TokenETC.PREFIX + loginResult);
            Map<String, String> map = new HashMap<>();
            map.put("message", "Login Success");
            map.put("userId", user.getUserId());
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}