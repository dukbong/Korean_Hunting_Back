package com.hangulhunting.Korean_Hunting.controller;

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
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
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
    @DisplayName("아이디 중복 o 저장 x")
    void userIdDuplicateErrorTest() {
        // 중복된 사용자 ID를 가진 사용자 생성
    	User user = new User("test1", "1234", "test1@gmail.com", "iit");
    	
        Mockito.when(userService.userIdCheck(user.getUserId())).thenReturn(true);
        
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
    	
    	Mockito.when(userService.userIdCheck(user.getUserId())).thenReturn(false);
    	
    	ResponseEntity<String> result = basicController.signup(user);
    	
    	Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    	Assertions.assertThat(result.getBody()).isEqualTo("회원 가입에 성공하였습니다.");
    	
    	Mockito.verify(userService, Mockito.times(1)).userIdCheck("test1");
    	Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(UserEntity.class));
    }
    
    @Test
    @DisplayName("아이디 빈문자열 o 저장 x")
    void userIdEmptyStringTest() {
    	User user = new User("", "1234", "test1@gmail.com", "iit");
    	
    	Mockito.when(userService.existsByUserIdIsNullOrBlank(user.getUserId())).thenReturn(true);
    	
    	ResponseEntity<String> result = basicController.signup(user);
    	
    	Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    	Assertions.assertThat(result.getBody()).isEqualTo("아이디는 필수 사항입니다.");
    	
    	Mockito.verify(userService, Mockito.never()).userIdCheck(Mockito.any());
    	Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
    }
    
    @Test
    @DisplayName("아이디 Null o 저장 x")
    void userIdNullTest() {
    	User user = new User(null, "1234", "test@gmail.com", "iit");
    	
    	Mockito.when(userService.existsByUserIdIsNullOrBlank(user.getUserId())).thenReturn(true);
    	
    	ResponseEntity<String> result = basicController.signup(user);
    	
    	Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    	Assertions.assertThat(result.getBody()).isEqualTo("아이디는 필수 사항입니다.");
    	
    	Mockito.verify(userService, Mockito.never()).userIdCheck(Mockito.any());
    	Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
    }
    
    @Test
    @DisplayName("예외 발생 시 회원가입 실패")
    void signupFailureTest() {
    	User user = new User("test1", "1234", "test1@gmail.com", "iit");
    	
    	Mockito.when(userRepository.save(Mockito.any(UserEntity.class))).thenThrow(new RuntimeException());
    	
    	ResponseEntity<String> result = basicController.signup(user);
    	
    	Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    	Assertions.assertThat(result.getBody()).isEqualTo("회원 가입에 실패하였습니다.");
    }
    
    @Test
    @DisplayName("비밀번호 null o 저장 x")
    void userPwdNullTest() {
    	User user = new User("test1", "", "test1@gmail.com", "iit");
    	
    	Mockito.when(userService.existsByUserPwdIsNullOrBlank(user.getUserPwd())).thenReturn(true);
    	
    	ResponseEntity<String> result = basicController.signup(user);
    	
    	Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    	Assertions.assertThat(result.getBody()).isEqualTo("비밀번호는 필수 사항입니다.");
    	
    	Mockito.verify(userService, Mockito.never()).userIdCheck(Mockito.any());
    	Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
    }
    
    @Test
    @DisplayName("비밀번호 빈문자열 o 저장 x")
    void userPwdEmptyStringTest() {
    	User user = new User("test1", null, "test1@gmail.com", "iit");
    	
    	Mockito.when(userService.existsByUserPwdIsNullOrBlank(user.getUserPwd())).thenReturn(true);
    	
    	ResponseEntity<String> result = basicController.signup(user);
    	
    	Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    	Assertions.assertThat(result.getBody()).isEqualTo("비밀번호는 필수 사항입니다.");
    	
    	Mockito.verify(userService, Mockito.never()).userIdCheck(Mockito.any());
    	Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
    }
    
    @Test
    @DisplayName("이메일 유효성 검사")
    void userEmailValid() {
    	User user = new User("test1", "1234", "test1gmail.com", "iit");
    	
    	Mockito.when(userService.isValidEmail(user.getEmail())).thenReturn(true);
    	
    	ResponseEntity<String> result = basicController.signup(user);
    	
    	Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    	Assertions.assertThat(result.getBody()).isEqualTo("이메일 형식이 잘못되었습니다.");
    	
    	Mockito.verify(userService, Mockito.times(1)).isValidEmail(user.getEmail());
    	Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(UserEntity.class));
    }
    

}
