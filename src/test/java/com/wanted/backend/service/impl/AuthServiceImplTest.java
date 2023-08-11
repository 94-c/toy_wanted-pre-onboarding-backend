package com.wanted.backend.service.impl;

import com.wanted.backend.dto.request.SignUpRequestDto;
import com.wanted.backend.dto.response.TokenResponse;
import com.wanted.backend.entity.User;
import com.wanted.backend.exception.GlobalException;
import com.wanted.backend.jwt.provider.JwtTokenProvider;
import com.wanted.backend.repository.AuthRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @InjectMocks
    private AuthServiceImpl authService;
    @Mock
    private AuthRepository authRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("회원가입 성공")
    void test_sign_up_success() {
        // Given
        final SignUpRequestDto signUpRequestDto = SignUpRequestDto.builder()
                .email("test@gmail.com")
                .password("password123")
                .name("테스트")
                .build();

        when(authRepository.findByEmail(signUpRequestDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(signUpRequestDto.getPassword())).thenReturn("encodedPassword");
        when(authRepository.save(any(User.class))).thenReturn(User.of(signUpRequestDto.getEmail(), signUpRequestDto.getPassword(), signUpRequestDto.getName()));

        // When
        User resultUser = authService.signUp(signUpRequestDto);

        // Then
        assertThat(resultUser).isNotNull();
        verify(authRepository).findByEmail(signUpRequestDto.getEmail());
        verify(passwordEncoder).encode(signUpRequestDto.getPassword());
        verify(authRepository).save(any(User.class));
    }

    @Test
    @DisplayName("로그인 성공")
    void test_login_success() {
        // Given
        final String email = "test@gmail.com";
        final String password = "password123";
        final Authentication authentication = mock(Authentication.class);
        final TokenResponse tokenResponse = TokenResponse.builder()
                .accessToken("123123123123")
                .build();

        when(authenticationManagerBuilder.getObject().authenticate(any())).thenReturn(authentication);
        when(jwtTokenProvider.createTokenResponse(authentication)).thenReturn(tokenResponse);

        // When
        TokenResponse resultTokenResponse = authService.login(email, password);

        // Then
        assertThat(resultTokenResponse).isEqualTo(tokenResponse);
        verify(authenticationManagerBuilder.getObject()).authenticate(any());
        verify(jwtTokenProvider).createTokenResponse(authentication);
    }

    @Test
    @DisplayName("로그인 실패 - 회원 미존재")
    void test_login_failure_member_not_found() {
        // Given
        final String email = "nonexistent@gmail.com";
        final String password = "wrongPassword";

        when(authenticationManagerBuilder.getObject().authenticate(any())).thenThrow(AuthenticationException.class);

        // When, Then
        assertThrows(GlobalException.class, () -> authService.login(email, password));
        verify(authenticationManagerBuilder.getObject()).authenticate(any());
    }
}

