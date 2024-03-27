package com.hangulhunting.Korean_Hunting.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import com.hangulhunting.Korean_Hunting.jwt.etc.TokenETC;
import com.hangulhunting.Korean_Hunting.serviceImpl.ApiService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApiAccessInterceptor implements WebRequestInterceptor{

	private final ApiService apiService;
	
	@Override
	public void preHandle(WebRequest request) throws Exception {
		String token = request.getHeader(TokenETC.AUTHORIZATION);
		apiService.apiTokenCheck(token.substring(7));
	}

	@Override
	public void postHandle(WebRequest request, ModelMap model) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(WebRequest request, Exception ex) throws Exception {
		// TODO Auto-generated method stub
		
	}
}