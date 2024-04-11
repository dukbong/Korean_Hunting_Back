package com.hangulhunting.Korean_Hunting.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.dto.response.UserResDto;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;
import com.hangulhunting.Korean_Hunting.serviceImpl.RegisterUserService;

@ExtendWith(MockitoExtension.class)
public class RegisterUserServiceTDD {

	@Mock
	UserRepository userRepository;
	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@InjectMocks
	RegisterUserService registerUserService;
	
	/***
	 * 요구 사항
	 * 1. 아이디는 중복되면 "아이디가 중복되었습니다." 오류 발생
	 * 2. 아이디가 비어있거나 null이면 안된다.
	 * 3. 비밀번호는 비어있거나 null이면 안된다.
	 * 4. 이메일은 이메일 형식을 지켜야한다.
	 * 5. 이메일은 비어있거나 null이면 안된다.
	 * 6. 비밀번호에는 대문자가 1개 이상 들어가야한다.
	 * 7. 비밀번호에는 소문자가 1개 이상 들어가야한다.
	 * 8. 비밀번호에는 숫자가 1개 이상 들어가야한다.
	 * 9. 비밀번호에는 특수문자가 1개 이상 들어가야한다.
	 * 10. 비밀번호는 8자 이상이여야한다.
	 * 11. github 로그인 하는 경우 비밀번호의 유효성 검사는 하지 않는다.
	 */
	
	@Test
	public void testRegisterUser_DuplicateUsername_Failure() {
		// given
		User user = User.builder().userId("testId").userPwd("Test12345!").company("testCompany1").email("test@gmail.com").build();
		
		when(userRepository.existsByUserId(user.getUserId())).thenReturn(true);
		
		// when
		RuntimeException runTimeException = assertUserRegistrationFails(user);
		
		// then
		Assertions.assertEquals("아이디가 중복되었습니다.", runTimeException.getMessage());
		
		verify(userRepository, times(1)).existsByUserId(user.getUserId());
	}
	
	@Test
	public void testRegisterUser_EmptyUsername_Failure() {
		// given
		User user = User.builder().userId("").userPwd("Test12345!").company("testCompany1").email("test@gmail.com").build();
		
		// when
		RuntimeException runTimeException = assertUserRegistrationFails(user);
		
		// then
		Assertions.assertEquals("아이디는 필수 입력 사항입니다.", runTimeException.getMessage());
	}
	
	@Test
	public void testRegisterUser_NullUsername_Failure() {
		// given
		User user = User.builder().userId(null).userPwd("Test12345!").company("testCompany1").email("test@gmail.com").build();
		
		// when
		RuntimeException runTimeException = assertUserRegistrationFails(user);
		
		// then
		Assertions.assertEquals("아이디는 필수 입력 사항입니다.", runTimeException.getMessage());
	}
	
	@Test
	public void testRegisterUser_EmptyUserPassword_Failure() {
		// given
		User user = User.builder().userId("test").userPwd("").company("testCompany1").email("test@gmail.com").build();
		
		// when
		RuntimeException runTimeException = assertUserRegistrationFails(user);
		
		// then
		Assertions.assertEquals("비밀번호는 필수 입력 사항입니다.", runTimeException.getMessage());
	}
	
	@Test
	public void testRegisterUser_NullUserPassword_Failure() {
		// given
		User user = User.builder().userId("test").userPwd(null).company("testCompany1").email("test@gmail.com").build();
		
		// when
		RuntimeException runTimeException = assertUserRegistrationFails(user);
		
		// then
		Assertions.assertEquals("비밀번호는 필수 입력 사항입니다.", runTimeException.getMessage());
	}
	
    @ParameterizedTest
    @CsvSource({
        "test@example.com",
        "user123@test.co.uk",
        "user.test@test.com",
        "user+tag@test.com",
        "user.test123@test.co.in",
        "user.test-123@test.co.jp"
    })
    public void testValidEmailFormat(String email) {
        // given
        User user = User.builder().userId("test").userPwd("Test12345!").company("testCompany1").email(email).build();

        // when
        try {
        	registerUserService.registerUser(user);
        } catch (RuntimeException e) {
            // then
            Assertions.fail("예외가 발생하지 않아야 합니다.");
        }
    }
	
	@Test
	public void testRegisterUser_EmptyEmail_Failure() {
		// given
		User user = User.builder().userId("test").userPwd("Test12345!").company("testCompany1").email("").build();
		
		// when
		RuntimeException runTimeException = assertUserRegistrationFails(user);
		
		// then
		Assertions.assertEquals("이메일은 필수 입력 사항입니다.", runTimeException.getMessage());
	}
	
	@Test
	public void testRegisterUser_NullUserEmail_Failure() {
		// given
		User user = User.builder().userId("test").userPwd("Test12345!").company("testCompany1").email(null).build();
		
		// when
		RuntimeException runTimeException = assertUserRegistrationFails(user);
		
		// then
		Assertions.assertEquals("이메일은 필수 입력 사항입니다.", runTimeException.getMessage());
	}
	
	@Test
	public void testRegisterUser_Success() {
		// given
		User user = User.builder().userId("testId").userPwd("Test12345!").company("testCompany").email("test@gmail.com").build();
		when(userRepository.existsByUserId(user.getUserId())).thenReturn(false);
		
		// when
		UserResDto dto = registerUserService.registerUser(user);
		
		// then
		Assertions.assertEquals("회원가입을 축하드립니다.", dto.getMessage());
		verify(userRepository,times(1)).existsByUserId(user.getUserId());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"a", "abcd123785", "SESDFSDFSDFDFG","Password12345"})
	public void testPasswordValidation(String password) {
		// given
		User user = User.builder().userId("testId").userPwd(password).company("testCompany").email("test@gmail.com").build();
		when(userRepository.existsByUserId(user.getUserId())).thenReturn(false);
		
		// when
		if(password.length() < 8) {
			RuntimeException runTimeException = assertUserRegistrationFails(user);
			
			//then
			Assertions.assertEquals("비밀번호는 8자리 이상이여야 합니다.", runTimeException.getMessage());
			return;
		}
		
		// when
		if(!password.matches(".*[A-Z].*")) {
			RuntimeException runTimeException = assertUserRegistrationFails(user);
			
			//then
			Assertions.assertEquals("비밀번호는 대문자를 한개 이상 포함해야합니다.", runTimeException.getMessage());
			return;
		}
		
		// when
		if(!password.matches(".*[a-z].*")) {
			RuntimeException runTimeException = assertUserRegistrationFails(user);
			
			//then
			Assertions.assertEquals("비밀번호는 소문자를 한개 이상 포함해야합니다.", runTimeException.getMessage());
			return;
		}
		
		// when
		if(!password.matches(".*[!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*")) {
			RuntimeException runTimeException = assertUserRegistrationFails(user);
			
			//then
			Assertions.assertEquals("비밀번호는 특수문자를 한개 이상 포함해야합니다.", runTimeException.getMessage());
			return;
		}
		
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"", "GitHub"})
	public void testGithubLoginWithoutPasswordValidation(String joinRoute) {
		// given
		User user = User.builder().userId("test").userPwd("aes45898t1").company("testCompany1").email("test@gmail.com").joinRoute(joinRoute).build();
		
		// when
		if(joinRoute.isEmpty()) {
			RuntimeException runTimeException = assertUserRegistrationFails(user);
			// then
			Assertions.assertEquals("비밀번호는 대문자를 한개 이상 포함해야합니다.", runTimeException.getMessage());
		}
		
		// when
		if(!joinRoute.isEmpty()) {
			try {
				registerUserService.registerUser(user);
			} catch (Exception e) {
				Assertions.fail("예외가 발생하지 않아야 합니다.");
			}
		}
	}
	
	private RuntimeException assertUserRegistrationFails(User user) {
		return Assertions.assertThrows(RuntimeException.class, () -> {
			registerUserService.registerUser(user);
									  });
	}
	
}
