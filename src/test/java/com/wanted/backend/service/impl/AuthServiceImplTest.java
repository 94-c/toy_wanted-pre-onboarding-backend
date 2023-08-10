package com.wanted.backend.service.impl;

import com.wanted.backend.dto.request.SignUpRequestDto;
import com.wanted.backend.dto.response.TokenResponse;
import com.wanted.backend.entity.User;
import com.wanted.backend.jwt.provider.JwtTokenProvider;
import com.wanted.backend.repository.AuthRepository;
import com.wanted.backend.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class AuthServiceImplTest {
    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private AuthRepository authRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignUp() {
        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .email("test@example.com")
                .password("password123")
                .name("Test User")
                .build();

        when(authRepository.findByEmail(signUpRequestDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(signUpRequestDto.getPassword())).thenReturn("encodedPassword");
        when(authRepository.save(any(User.class))).thenReturn(User.builder()
                .email(signUpRequestDto.getEmail())
                .password(signUpRequestDto.getPassword())
                .name(signUpRequestDto.getName())
                .build());

        User result = authService.signUp(signUpRequestDto);

        verify(authRepository, times(1)).findByEmail(signUpRequestDto.getEmail());
        verify(passwordEncoder, times(1)).encode(signUpRequestDto.getPassword());
        verify(authRepository, times(1)).save(any(User.class));

    }

    @Test
    void testLogin() {
        String email = "hyeongwoo26@gmail.com";
        String password = "1234";

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        when(authenticationManager.authenticate(authenticationToken)).thenReturn(mock(Authentication.class));
        when(jwtTokenProvider.createTokenResponse(any(Authentication.class))).thenReturn(TokenResponse.builder()
                .build());

        TokenResponse result = authService.login(email, password);

        verify(authenticationManager, times(1)).authenticate(authenticationToken);
        verify(jwtTokenProvider, times(1)).createTokenResponse(any(Authentication.class));

        // Add assertions here to verify the 'result' object or other expectations
    }
}
