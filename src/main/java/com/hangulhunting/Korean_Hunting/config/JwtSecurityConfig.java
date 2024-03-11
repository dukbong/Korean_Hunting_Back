//package com.hangulhunting.Korean_Hunting.config;
//
//import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.hangulhunting.Korean_Hunting.jwt.filter.JwtFilter;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@RequiredArgsConstructor
//@Slf4j
//public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
//	
//	private final JwtFilter jwtFilter;
////    private final TokenProvider tokenProvider;
////    private final BlackListRepository blackListRepository;
////    private final RefreshTokenRepository refreshTokenRepository;
////    private final UserRepository userRepositorsy;
//    
//    @Override
//    public void configure(HttpSecurity http) {
//    	log.info("-------------------------------- start 궁금");
////        JwtFilter customFilter = new JwtFilter(tokenProvider, blackListRepository, userRepository, refreshTokenRepository);
//        JwtFilter customFilter = jwtFilter;
//        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//}
