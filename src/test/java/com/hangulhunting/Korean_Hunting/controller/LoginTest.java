package com.hangulhunting.Korean_Hunting.controller;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

import com.hangulhunting.Korean_Hunting.dto.PrincipalDetails;
import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.dto.UserRole;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;
import com.hangulhunting.Korean_Hunting.service.AcountService;
import com.hangulhunting.Korean_Hunting.service.UserService;

@ExtendWith(MockitoExtension.class)
public class LoginTest {
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Mock
	UserService userService;
	
	@Mock
	AcountService acountService;
	
	@InjectMocks
	BasicController basicController;
	
	@BeforeEach
	void before() {
		UserEntity user = UserEntity.builder().userId("test1").userPwd("1234").role(UserRole.USER).email("test1@gmail.com").company("iit").build();
		userRepository.save(user);
	}
	
	@Test
	@DisplayName("로그인 성공")
	void loginSuccess() {
        User user = new User("test2", "1234", "test1@gmail.com", "iit");
        PrincipalDetails principalDetails = new PrincipalDetails(UserEntity.builder().userId(user.getUserId()).userPwd(user.getUserPwd()).build());
        Mockito.when(acountService.loadUserByUsername(user.getUserId())).thenReturn(principalDetails);

        ResponseEntity<String> result = basicController.login(user);

        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(result.getBody()).isEqualTo("[로그인 성공] ID : test1" + ", PWD : " + user.getUserPwd());
	}
	
}
