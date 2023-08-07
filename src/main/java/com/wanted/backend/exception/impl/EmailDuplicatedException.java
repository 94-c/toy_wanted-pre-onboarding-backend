package com.wanted.backend.exception.impl;

import com.wanted.backend.exception.CustomException;
import com.wanted.backend.exception.ErrorCode;

public class EmailDuplicatedException extends CustomException {
    public EmailDuplicatedException() {
        super(ErrorCode.EMAIL_DUPLICATED);
    }
}
