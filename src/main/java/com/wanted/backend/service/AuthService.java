package com.wanted.backend.service;

import com.wanted.backend.dto.request.LoginRequestDto;
import com.wanted.backend.dto.request.SignUpRequestDto;
import com.wanted.backend.entity.User;

public interface AuthService {
    String signUp(SignUpRequestDto signUpRequestDto);
    String login(LoginRequestDto loginRequestDto);

}
