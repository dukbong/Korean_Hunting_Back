package com.hangulhunting.Korean_Hunting.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.dto.UserResDto;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;
import com.hangulhunting.Korean_Hunting.service.UserService;

@ExtendWith(MockitoExtension.class)
public class SignUpTest {

	@Mock
	UserRepository userRepository;

	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Mock
	UserService userService;

	@InjectMocks
	BasicController basicController;

	@Test
	@DisplayName("이미 사용 중인 ID입니다.")
	void userIdDuplicateErrorTest() {
		// given
		User user = new User("test1", "1234", "test1@gmail.com", "iit");
		Mockito.when(userService.joinProcess(user))
			   .thenThrow(new CustomException(ErrorCode.NAME_ALREADY_EXISTS, user.getUserId()));
		
		// when
	    Throwable thrown = assertThrows(CustomException.class, () -> {
	        basicController.signup(user);
	    });
		
		// then
	    Assertions.assertThat(thrown)
        .hasMessage("해당 이름은 이미 존재하는 멤버입니다: " + user.getUserId())
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.NAME_ALREADY_EXISTS);
	}
	
}
