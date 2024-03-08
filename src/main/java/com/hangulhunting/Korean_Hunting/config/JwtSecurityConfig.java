package com.hangulhunting.Korean_Hunting.config;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hangulhunting.Korean_Hunting.jwt.TokenProvider;
import com.hangulhunting.Korean_Hunting.jwt.filter.JwtFilter;
import com.hangulhunting.Korean_Hunting.repository.BlackListRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	
    private final TokenProvider tokenProvider;
    private final BlackListRepository blackListRepository;
    
    @Override
    public void configure(HttpSecurity http) {
        JwtFilter customFilter = new JwtFilter(tokenProvider, blackListRepository);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
