package com.hangulhunting.Korean_Hunting.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.hangulhunting.Korean_Hunting.jwt.filter.JwtFilter;
import com.hangulhunting.Korean_Hunting.jwt.handler.JwtAccessDeniedHandler;
import com.hangulhunting.Korean_Hunting.jwt.handler.JwtAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtFilter jwtFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth.requestMatchers("/login").permitAll() // 로그인
				.requestMatchers("/join").permitAll() // 회원가입
				.requestMatchers("/admin/**").hasRole("ADMIN") // 관리자 페이지
				.requestMatchers("/info/**").hasAnyRole("USER", "ADMIN") // 관리자 페이지
				.requestMatchers("/file/**").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/api/**").hasRole("API")
				.anyRequest().authenticated());

		http.logout(logout -> logout.disable());
		
		http.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.formLogin(auth -> auth.disable());

		http.csrf(auth -> auth.disable());

		http.cors(cors -> {
			cors.configurationSource(corsConfigurationSource());
		});
		
		http.exceptionHandling(handling -> handling
													.accessDeniedHandler(jwtAccessDeniedHandler)
													.authenticationEntryPoint(jwtAuthenticationEntryPoint)
		);
		
	    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}