package com.wanted.backend.service.impl;

import com.wanted.backend.dto.request.SignUpRequestDto;
import com.wanted.backend.dto.response.TokenResponse;
import com.wanted.backend.entity.User;
import com.wanted.backend.exception.impl.EmailDuplicatedException;
import com.wanted.backend.exception.impl.LoginInfoException;
import com.wanted.backend.jwt.provider.JwtTokenProvider;
import com.wanted.backend.repository.AuthRepository;
import com.wanted.backend.service.AuthService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        authRepository.findByEmail(signUpRequestDto.getEmail())
                .ifPresent(x -> {
                    throw new EmailDuplicatedException();
                });

        String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());
        User createUser = User.createNormalMember(signUpRequestDto.getEmail(), encodedPassword, signUpRequestDto.getName());

        return authRepository.save(createUser);
    }

    @Override
    public TokenResponse login(String email, String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            return jwtTokenProvider.generateToken(authentication);
        } catch (AuthenticationException e) {
            throw new LoginInfoException();
        }
    }
}
