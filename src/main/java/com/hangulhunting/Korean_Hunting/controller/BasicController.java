package com.hangulhunting.Korean_Hunting.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hangulhunting.Korean_Hunting.dto.PrincipalDetails;
import com.hangulhunting.Korean_Hunting.dto.User;
import com.hangulhunting.Korean_Hunting.dto.UserRole;
import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;
import com.hangulhunting.Korean_Hunting.service.AcountService;
import com.hangulhunting.Korean_Hunting.service.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BasicController {

	private final UserService userService;
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final AcountService acountService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {
		PrincipalDetails detail = (PrincipalDetails) acountService.loadUserByUsername(user.getUserId());
		Optional<UserEntity> entity = userRepository.findByUserId(detail.getUsername());
		
		// UserDetail 과 파라미터 비교
		bCryptPasswordEncoder.encode(user.getUserPwd()).equals(detail.getPassword());
		
		return ResponseEntity.ok("[로그인 성공] ID : " + detail.getUsername() + ", PWD : " + detail.getPassword());
	}

	@PostMapping("/signup")
	@Transactional
	public ResponseEntity<String> signup(@RequestBody User user) {
		try {
			if(userService.existsByUserIdIsNullOrBlank(user.getUserId())) {
				return new ResponseEntity<>("아이디는 필수 사항입니다.", HttpStatus.BAD_REQUEST);
			}
			
			if(userService.existsByUserPwdIsNullOrBlank(user.getUserPwd())) {
				return new ResponseEntity<>("비밀번호는 필수 사항입니다.", HttpStatus.BAD_REQUEST);
			}
			
			if(userService.isValidEmail(user.getEmail())) {
				return new ResponseEntity<>("이메일 형식이 잘못되었습니다.", HttpStatus.BAD_REQUEST);
			}
			
			if(userService.userIdCheck(user.getUserId())) {
				return new ResponseEntity<>("회원가입하려는 ID가 중복되었습니다.", HttpStatus.BAD_REQUEST);
			}
			
			userRepository.save(createUserEntity(user));
			return ResponseEntity.ok().body("회원 가입에 성공하였습니다.");
		} catch (Exception e) {
			return new ResponseEntity<>("회원 가입에 실패하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private UserEntity createUserEntity(User user) {
		return UserEntity.builder()
						 .userId(user.getUserId())
						 .userPwd(bCryptPasswordEncoder.encode(user.getUserPwd()))
						 .email(user.getEmail())
						 .company(user.getCompany())
						 .role(UserRole.USER)
						 .build();
	}

}
