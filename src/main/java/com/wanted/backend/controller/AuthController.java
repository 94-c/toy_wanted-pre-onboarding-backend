package com.wanted.backend.controller;

import com.wanted.backend.dto.SuccessResponse;
import com.wanted.backend.dto.request.LoginRequestDto;
import com.wanted.backend.dto.request.SignUpRequestDto;
import com.wanted.backend.dto.response.SignUpResponse;
import com.wanted.backend.dto.response.TokenResponse;
import com.wanted.backend.entity.User;
import com.wanted.backend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/signup")
    @ResponseStatus(value = HttpStatus.CREATED)
    public SuccessResponse createUser(@Valid @RequestBody SignUpRequestDto signupDTO) {
        User user = authService.signUp(signupDTO);

        return SuccessResponse.success(SignUpResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .build());
    }

    @PostMapping("/login")
    public SuccessResponse login(@RequestBody @Validated LoginRequestDto dto) {

        TokenResponse token = authService.login(dto.getEmail(), dto.getPassword());

        return SuccessResponse.success(TokenResponse.builder()
                .grantType(token.getGrantType())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .build());
    }

}
