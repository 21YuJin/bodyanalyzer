package com.fitnessai.bodyanalyzer.controller;

import com.fitnessai.bodyanalyzer.dto.JwtResponse;
import com.fitnessai.bodyanalyzer.dto.LoginRequest;
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

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request,
                                             HttpServletResponse response) {
        AuthResult result = authService.authenticate(request);

        // Refresh Token을 HttpOnly 쿠키로 설정
        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", result.refreshToken())
                .httpOnly(true)
                .secure(false) // HTTPS 환경에서는 true 권장
                .path("/")
                .maxAge(7 * 24 * 60 * 60) // 7일
                .sameSite("Strict") // CSRF 방지
                .build();

        response.addHeader("Set-Cookie", refreshCookie.toString());

        // AccessToken만 응답 Body에 반환
        return ResponseEntity.ok(result.jwtResponse());
    }
}