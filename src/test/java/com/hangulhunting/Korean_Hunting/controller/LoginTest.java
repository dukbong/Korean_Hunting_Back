package com.hangulhunting.Korean_Hunting.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.hangulhunting.Korean_Hunting.dto.PrincipalDetails;
import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.service.UserService;

@ExtendWith(MockitoExtension.class)
public class LoginTest {

	@Test
	@DisplayName("로그인 성공")
	void loginSuccess() {
		AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
		UserService userService = mock(UserService.class);
		BasicController basicController = new BasicController(userService, authenticationManager);
		
		User user = new User("test1", "1234", "test1@gmail.com", "iit");
		UserEntity userEntity = UserEntity.builder().userId(user.getUserId()).company(user.getUserPwd()).build();
		
		when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserId(), user.getUserPwd())))
								  .thenReturn(new UsernamePasswordAuthenticationToken(new PrincipalDetails(userEntity), null));
		
		ResponseEntity<String> responseEntity = basicController.login(user);
		
		Assertions.assertThat(HttpStatus.OK).isEqualTo(responseEntity.getStatusCode());
		Assertions.assertThat("로그인 성공 : " + user.getUserId()).isEqualTo(responseEntity.getBody());
	}
	
	@Test
	@DisplayName("로그인 실패")
	void loginFailed() {
		AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
		UserService userService = mock(UserService.class);
		BasicController basicController = new BasicController(userService, authenticationManager);
		
		User user = new User("test1", "1234", "test1@gmail.com", "iit");
		
		when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserId(), user.getUserPwd())))
		.thenThrow(new RuntimeException("인증 실패"));
		
		ResponseEntity<String> responseEntity = basicController.login(user);
		
		Assertions.assertThat(HttpStatus.UNAUTHORIZED).isEqualTo(responseEntity.getStatusCode());
		Assertions.assertThat("로그인 실패 : " + user.getUserId()).isEqualTo(responseEntity.getBody());
	}
}
