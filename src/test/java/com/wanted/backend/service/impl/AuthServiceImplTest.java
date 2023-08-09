package com.wanted.backend.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.wanted.backend.dto.request.SignUpRequestDto;
import com.wanted.backend.dto.response.TokenResponse;
import com.wanted.backend.entity.User;
import com.wanted.backend.exception.impl.EmailDuplicatedException;
import com.wanted.backend.exception.impl.LoginInfoException;
import com.wanted.backend.jwt.provider.JwtTokenProvider;
import com.wanted.backend.repository.AuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private AuthRepository authRepository;

    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignUp_Success() {
        // Given
        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .email("hyeongwoo216@gmail.com")
                .password("1234")
                .name("테스트 계정")
                .build();

        when(authRepository.findByEmail(signUpRequestDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(signUpRequestDto.getPassword())).thenReturn("encodedPassword");

        // When
        User result = authService.signUp(signUpRequestDto);

        // Then
        assertNotNull(result);
        assertEquals(signUpRequestDto.getEmail(), result.getEmail());
        assertEquals("encodedPassword", result.getPassword());
        assertEquals(signUpRequestDto.getName(), result.getName());

        verify(authRepository, times(1)).findByEmail(signUpRequestDto.getEmail());
        verify(passwordEncoder, times(1)).encode(signUpRequestDto.getPassword());
        verify(authRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testSignUp_DuplicateEmail_Failure() {
        // Given
        SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .email("hyeongwoo26@gmail.com")
                .build();


        // When & Then
        assertThrows(EmailDuplicatedException.class, () -> authService.signUp(signUpRequestDto));

        verify(authRepository, times(1)).findByEmail(signUpRequestDto.getEmail());
        verify(passwordEncoder, never()).encode(anyString());
        verify(authRepository, never()).save(any(User.class));
    }

    @Test
    public void testLogin_Success() {
        // Given
        String email = "hyeongwoo26@gmail.com";
        String password = "1234";
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        when(authenticationManagerBuilder.getObject().authenticate(any(Authentication.class))).thenReturn(authentication);

        // When
        TokenResponse result = authService.login(email, password);

        // Then
        assertNotNull(result);

        verify(authenticationManagerBuilder.getObject(), times(1)).authenticate(any(Authentication.class));
        verify(jwtTokenProvider, times(1)).createTokenResponse(authentication);
    }

    @Test
    public void testLogin_InvalidCredentials_Failure() {
        // Given
        String email = "test@example.com";
        String password = "invalidPassword";
        when(authenticationManagerBuilder.getObject().authenticate(any(Authentication.class)))
                .thenThrow(new AuthenticationException("Invalid credentials") {});

        // When & Then
        assertThrows(LoginInfoException.class, () -> authService.login(email, password));

        verify(authenticationManagerBuilder.getObject(), times(1)).authenticate(any(Authentication.class));
        verify(jwtTokenProvider, never()).createTokenResponse(any(Authentication.class));
    }
}
