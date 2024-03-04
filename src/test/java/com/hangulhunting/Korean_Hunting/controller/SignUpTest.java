package com.hangulhunting.Korean_Hunting.controller;

import java.util.Optional;

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
import com.hangulhunting.Korean_Hunting.dto.UserRole;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;
import com.hangulhunting.Korean_Hunting.service.UserService;

@ExtendWith(MockitoExtension.class)
public class SignUpTest {

	@Mock
	UserRepository userRepository;
	
	@Mock
	UserService userService;
	
	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@InjectMocks
	BasicController basicController;
	
    @Test
    @DisplayName("아이디 중복 o 저장 x")
    void userIdDuplicateErrorTest() {
        // 중복된 사용자 ID를 가진 사용자 생성
    	User user = new User("test1", "1234", "test1@gmail.com", "iit");
    	
        Mockito.when(userService.userIdCheck("test1")).thenReturn(true);
        
        ResponseEntity<String> result = basicController.signup(user);

        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(result.getBody()).isEqualTo("회원가입하려는 ID가 중복되었습니다.");

        Mockito.verify(userService, Mockito.times(1)).userIdCheck("test1");
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
    }
    
    @Test
    @DisplayName("아이디 중복 x 저장 o")
    void userIdNotDuplicateAndSaveTest() {
    	User user = new User("test1", "1234", "test1@gmail.com", "iit");
    	
    	Mockito.when(userService.userIdCheck("test1")).thenReturn(false);
    	
    	ResponseEntity<String> result = basicController.signup(user);
    	
    	Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    	Assertions.assertThat(result.getBody()).isEqualTo("회원 가입에 성공하였습니다.");
    	
    	Mockito.verify(userService, Mockito.times(1)).userIdCheck("test1");
    	Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(UserEntity.class));
    }
    

}
