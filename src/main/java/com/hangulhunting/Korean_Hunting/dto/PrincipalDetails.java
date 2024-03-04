package com.hangulhunting.Korean_Hunting.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hangulhunting.Korean_Hunting.entity.UserEntity;

public class PrincipalDetails implements UserDetails{
	
	private UserEntity user;
	
	public PrincipalDetails(UserEntity user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collections = new ArrayList<>();
		collections.add(() -> {
			return user.getRole().name();
		});
		return collections;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getUserPwd();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUserId();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	

}
