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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.dto.response.UserResDto;
import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorCode;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;
import com.hangulhunting.Korean_Hunting.serviceImpl.RegisterUserService;

@ExtendWith(MockitoExtension.class)
public class SignUpTest {

	@Mock
	UserRepository userRepository;

	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Mock
	RegisterUserService userService;

	@InjectMocks
	BasicController basicController;

	@Test
	@DisplayName("비정상 로그인 : 이미 사용 중인 ID입니다.")
	void userIdAlredyExists() {
		// given
		User user = new User("test1", "1234", "test1@gmail.com", "iit");
		Mockito.when(userService.registerUser(user))
			   .thenThrow(new CustomException(ErrorCode.NAME_ALREADY_EXISTS, user.getUserId()));
		
		// when
	    Throwable thrown = assertThrows(CustomException.class, () -> {
	        basicController.join(user);
	    });
		
		// then
	    Assertions.assertThat(thrown)
        .hasMessage("해당 이름은 이미 존재하는 멤버입니다: " + user.getUserId())
        .hasFieldOrPropertyWithValue("errorCode", ErrorCode.NAME_ALREADY_EXISTS);
	}
	
	@Test
	@DisplayName("비정상 로그인 : ID는 빈문자열 || Null")
	void userIdEmptyOrNull() {
		// given
		User user = new User("", "1234", "test1@gmail.com", "iit");
		Mockito.when(userService.registerUser(user)).thenThrow(new CustomException(ErrorCode.MEMBER_IDS_IS_EMPTY_OR_NULL, user.getUserId()));
		
		// when
		Throwable thrown = assertThrows(CustomException.class, () -> {
			basicController.join(user);
		});
		
		// then
		Assertions.assertThat(thrown)
				  .hasMessage("멤버 ID 목록이 비어있거나 null 입니다: " + user.getUserId())
				  .hasFieldOrPropertyWithValue("errorCode", ErrorCode.MEMBER_IDS_IS_EMPTY_OR_NULL);
	}
	
	@Test
	@DisplayName("정상 로그인")
	void join() {
	    // given
	    User user = new User("test1", "1234", "test1@gmail.com", "iit");
	    Mockito.when(userService.registerUser(user)).thenReturn(new UserResDto("회원가입에 성공하였습니다."));

	    // when
	    UserResDto result = userService.registerUser(user);

	    // then
	    Assertions.assertThat(result).isNotNull();
	    Assertions.assertThat("회원가입에 성공하였습니다.").isEqualTo(result.getMessage());
	}
	
}
