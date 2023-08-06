package com.wanted.backend.controller;

import com.wanted.backend.dto.request.LoginRequestDto;
import com.wanted.backend.dto.request.SignUpRequestDto;
import com.wanted.backend.dto.response.LoginResponse;
import com.wanted.backend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping(value = {"/login"})
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequestDto loginDto){
        String token = authService.login(loginDto);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(token);

        return ResponseEntity.ok(loginResponse);
    }
    @PostMapping(value = {"/signup"})
    public ResponseEntity<String> signUp(@RequestBody SignUpRequestDto signUpRequestDto){

        String response = authService.signUp(signUpRequestDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
