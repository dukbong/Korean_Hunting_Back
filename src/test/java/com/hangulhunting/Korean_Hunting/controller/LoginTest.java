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

import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.dto.UserResDto;
import com.hangulhunting.Korean_Hunting.service.UserService;

@ExtendWith(MockitoExtension.class)
public class LoginTest {

	/*
	 * @Test
	 * 
	 * @DisplayName("로그인 성공") void loginSuccess() { UserService userService =
	 * mock(UserService.class); BasicController basicController = new
	 * BasicController(userService);
	 * 
	 * User user = new User("test1", "1234", "test1@gmail.com", "iit");
	 * 
	 * when(userService.loginProcess(user)).thenReturn(new UserResDto("로그인 성공 : " +
	 * user.getUserId()));
	 * 
	 * ResponseEntity<UserResDto> responseEntity = basicController.login(user);
	 * 
	 * Assertions.assertThat(HttpStatus.OK).isEqualTo(responseEntity.getStatusCode()
	 * ); Assertions.assertThat("로그인 성공 : " +
	 * user.getUserId()).isEqualTo(responseEntity.getBody().getMessage()); }
	 * 
	 * 
	 * @Test
	 * 
	 * @DisplayName("로그인 실패") void loginFailed() { UserService userService =
	 * mock(UserService.class); BasicController basicController = new
	 * BasicController(userService);
	 * 
	 * User user = new User("test1", "1234", "test1@gmail.com", "iit");
	 * 
	 * when(userService.loginProcess(user)).thenThrow(new
	 * RuntimeException("로그인 실패"));
	 * 
	 * ResponseEntity<UserResDto> responseEntity = basicController.login(user);
	 * 
	 * Assertions.assertThat(HttpStatus.UNAUTHORIZED).isEqualTo(responseEntity.
	 * getStatusCode()); Assertions.assertThat("로그인 실패 : " +
	 * user.getUserId()).isEqualTo(responseEntity.getBody().getMessage()); }
	 */

}
