package com.hangulhunting.Korean_Hunting.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
public class FileProcessingTimeCheck {
	
    @Before("execution(* com.hangulhunting.Korean_Hunting.serviceImpl.FileService.*(..))")
    public void logMethodStart(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Method {} is starting.", methodName);
    }

    @After("execution(* com.hangulhunting.Korean_Hunting.serviceImpl.FileService.*(..))")
    public void logMethodEnd(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Method {} has ended.", methodName);
    }
    
}
