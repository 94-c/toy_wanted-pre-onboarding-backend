package com.wanted.backend.exception;

public class CustomException extends RuntimeException {
    public CustomException(ErrorCode code) {
        super(code.getMessage());
    }
}