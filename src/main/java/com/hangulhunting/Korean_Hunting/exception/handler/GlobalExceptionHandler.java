package com.hangulhunting.Korean_Hunting.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hangulhunting.Korean_Hunting.exception.CustomException;
import com.hangulhunting.Korean_Hunting.exception.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class, RuntimeException.class})
    protected ResponseEntity<ErrorResponse> catchException(Exception e) {
        log.error(e.getMessage());
        log.error("{}", e);
        return ResponseEntity.internalServerError().body(new ErrorResponse(e.getMessage(), e.toString()));
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(new ErrorResponse(e.getMessage(), e.toString()));
    }

}
