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

import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class BasicControllerTest {

	@Mock
	UserRepository userRepository;
	
	@InjectMocks
	BasicController basicController;
	
    @Test
    @DisplayName("아이디 중복 o 저장 x")
    void userIdDuplicateErrorTest() {
        // 중복된 사용자 ID를 가진 사용자 생성
    	User user = new User("test1", "1234", "test1@gmail.com", "iit");
    	
        // 중복된 사용자 ID를 가진 사용자를 찾았다고 가정
        Mockito.when(userRepository.findByUserId("test1")).thenReturn(Optional.of(new UserEntity()));

        // signup 메서드 호출
        ResponseEntity<Object> response = basicController.signup(user);

        // 응답 확인
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(response.getBody()).isEqualTo("아이디 중복 오류");
        
        // findUserId 메소드가 한번만 호출되었는지 검증
        Mockito.verify(userRepository, Mockito.times(1)).findByUserId("test1");
        
        // 아이디 중복시 save는 호출되지 않았는지 검증
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
    }
    
    @Test
    @DisplayName("아이디 중복 x 저장 o")
    void userIdSaveTest() {
    	User user = new User("test1", "1234", "test@gmail.com", "iit");
    	
    	Mockito.when(userRepository.findByUserId("test1")).thenReturn(Optional.empty());
    	
    	ResponseEntity<Object> response = basicController.signup(user);
    	
    	Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    	Assertions.assertThat(response.getBody()).isEqualTo("회원 가입에 성공하였습니다.");
    	
    	// 중복 확인을 위해 한번 실행되었는지 검증
    	Mockito.verify(userRepository, Mockito.times(1)).findByUserId("test1");
    	// 저장을 위해 한번 실행되었는지 검증
    	Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(UserEntity.class));
    }

}
