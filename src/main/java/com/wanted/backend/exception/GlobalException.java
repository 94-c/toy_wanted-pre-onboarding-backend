package com.wanted.backend.exception;

import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {
    private final String errorCode;
    public GlobalException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.toString();
    }
}