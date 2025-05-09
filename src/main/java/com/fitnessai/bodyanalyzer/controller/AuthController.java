package com.fitnessai.bodyanalyzer.controller;

import com.fitnessai.bodyanalyzer.domain.User;
import com.fitnessai.bodyanalyzer.dto.JwtResponse;
import com.fitnessai.bodyanalyzer.dto.LoginRequest;
import com.fitnessai.bodyanalyzer.dto.UserDto;
import com.fitnessai.bodyanalyzer.service.AuthService;
import com.fitnessai.bodyanalyzer.service.AuthService.AuthResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody UserDto dto,
                                                HttpServletResponse response) {
        authService.registerUser(dto);

        // 회원가입 후 자동 로그인 처리
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(dto.getEmail());
        loginRequest.setPassword(dto.getPassword());

        return getJwtResponseResponseEntity(response, loginRequest);
    }


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request,
                                             HttpServletResponse response) {
        return getJwtResponseResponseEntity(response, request);
    }

    private ResponseEntity<JwtResponse> getJwtResponseResponseEntity(HttpServletResponse response, LoginRequest loginRequest) {
        AuthResult result = authService.authenticate(loginRequest);

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", result.refreshToken())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Strict")
                .build();

        response.addHeader("Set-Cookie", refreshCookie.toString());
        response.setHeader("Authorization", "Bearer " + result.accessToken());

        return ResponseEntity.ok(result.jwtResponse());
    }

}