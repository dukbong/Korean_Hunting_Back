package com.hangulhunting.Korean_Hunting.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityUtil {

    private SecurityUtil() { }

    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
        }
        
        log.info("get Name () = > {}", authentication.getName());
        log.info("get Name parse Long = > {}", Long.parseLong(authentication.getName()));
        
        return Long.parseLong(authentication.getName());
    }
}