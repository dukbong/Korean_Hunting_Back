//package com.hangulhunting.Korean_Hunting.config;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Aspect
//@Component
//@Slf4j
//public class ExecutionTimeLoggerAspect {
//    
//    @Pointcut("execution(* com.hangulhunting.Korean_Hunting.serviceImpl.file.*.*(..))")
//    public void serviceMethods() {}
//
//    @Around("serviceMethods()")
//    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
//        long startTime = System.currentTimeMillis();
//        Object result = joinPoint.proceed();
//        long endTime = System.currentTimeMillis();
//        log.info("Method {} execution time: {} ms", joinPoint.getSignature().toShortString(), (endTime - startTime));
//        return result;
//    }
//}