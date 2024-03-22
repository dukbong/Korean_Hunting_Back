package com.hangulhunting.Korean_Hunting.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = -6458499530147326804L;
	private final ErrorCode errorCode;

    // ErrorCode 대신에, 여기서 throw~ 함수 만들기

    public CustomException(ErrorCode errorCode, Long num) {
        super(String.format(errorCode.getDetail(), num));
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, String detail) { // ErrorCode 의 detail 에 여기서의 detail 이 들어감.
        super(String.format(errorCode.getDetail(), detail));
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getDetail());
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {
        return errorCode.getHttpStatus();
    }
}