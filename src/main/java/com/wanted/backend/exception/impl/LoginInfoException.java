package com.wanted.backend.exception.impl;

import com.wanted.backend.exception.CustomException;
import com.wanted.backend.exception.ErrorCode;

public class LoginInfoException extends CustomException {
    public LoginInfoException() {
        super(ErrorCode.LOGIN_INFO_INCORRECT);
    }
}
