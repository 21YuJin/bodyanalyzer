package com.fitnessai.bodyanalyzer.service;

import com.fitnessai.bodyanalyzer.domain.User;
import com.fitnessai.bodyanalyzer.dto.JwtResponse;
import com.fitnessai.bodyanalyzer.dto.LoginRequest;
import com.fitnessai.bodyanalyzer.repository.UserRepository;
import com.fitnessai.bodyanalyzer.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public record AuthResult(String accessToken, String refreshToken, JwtResponse jwtResponse) {}

    public AuthResult authenticate(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUsername());

        JwtResponse jwtResponse = new JwtResponse(accessToken, user.getUsername(), user.getEmail());
        return new AuthResult(accessToken, refreshToken, jwtResponse);
    }
}
