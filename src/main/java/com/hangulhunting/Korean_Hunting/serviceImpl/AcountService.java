package com.hangulhunting.Korean_Hunting.serviceImpl;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hangulhunting.Korean_Hunting.entity.UserEntity;
import com.hangulhunting.Korean_Hunting.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AcountService implements UserDetailsService{
	
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUserId(username)
				      		 .map(this::createUserDetails)
							 .orElseThrow(() -> new UsernameNotFoundException("아이디를 확인해주세요. : " + username));
	}
	
    private UserDetails createUserDetails(UserEntity user) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole().toString());

        return new User(
                String.valueOf(user.getUserId()),
                user.getUserPwd(),
                Collections.singleton(grantedAuthority)
        );
    }
}
