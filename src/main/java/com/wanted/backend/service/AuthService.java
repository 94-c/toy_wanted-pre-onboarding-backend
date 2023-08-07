package com.wanted.backend.service;

import com.wanted.backend.dto.request.SignUpRequestDto;
import com.wanted.backend.dto.response.TokenResponse;
import com.wanted.backend.entity.User;

public interface AuthService {
    User signUp(SignUpRequestDto signUpRequestDto);
    TokenResponse login(String email, String password);
}
