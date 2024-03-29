package com.hangulhunting.Korean_Hunting.exception;

import java.util.List;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final String message;
    private final List<String> errors;

    // 에러가 여러개 일때
    public ErrorResponse(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    // 에러가 1개 일때
    public ErrorResponse(String message, String err) {
        this.message = message;
        this.errors = List.of(err);
    }

	public static ErrorResponse fromErrorCodeToResponse(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.name(), errorCode.getDetail());
    }
}