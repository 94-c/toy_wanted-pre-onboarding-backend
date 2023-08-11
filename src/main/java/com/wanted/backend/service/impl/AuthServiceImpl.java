package com.wanted.backend.service.impl;

import com.wanted.backend.dto.request.SignUpRequestDto;
import com.wanted.backend.dto.response.TokenResponse;
import com.wanted.backend.entity.User;
import com.wanted.backend.exception.ErrorCode;
import com.wanted.backend.exception.GlobalException;
import com.wanted.backend.jwt.provider.JwtTokenProvider;
import com.wanted.backend.repository.AuthRepository;
import com.wanted.backend.service.AuthService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    public AuthServiceImpl(AuthRepository authRepository, AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.authRepository = authRepository;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User signUp(SignUpRequestDto signUpRequestDto) {
        if (authRepository.findByEmail(signUpRequestDto.getEmail()).isPresent()) {
            throw new GlobalException(ErrorCode.EMAIL_DUPLICATED);
        }

        if (!signUpRequestDto.getEmail().contains("@")) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED);
        }

        if(signUpRequestDto.getPassword().length() < 8){
            throw new GlobalException(ErrorCode.WRONG_PASSWORD_INFO);
        }

        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());

        User createUser = User.of(signUpRequestDto.getEmail(), encodedPassword, signUpRequestDto.getName());
        createUser.setCreatedAt(LocalDateTime.now());

        return authRepository.save(createUser);
    }

    @Override
    public TokenResponse login(String email, String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            return jwtTokenProvider.createTokenResponse(authentication);

        } catch (AuthenticationException e) {
            throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }

}
