package com.fitnessai.bodyanalyzer.service;

import com.fitnessai.bodyanalyzer.domain.User;
import com.fitnessai.bodyanalyzer.dto.JwtResponse;
import com.fitnessai.bodyanalyzer.dto.LoginRequest;
import com.fitnessai.bodyanalyzer.dto.UserDto;
import com.fitnessai.bodyanalyzer.repository.UserRepository;
import com.fitnessai.bodyanalyzer.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public record AuthResult(String accessToken, String refreshToken, JwtResponse jwtResponse) {}

    public AuthResult authenticate(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        JwtResponse jwtResponse = new JwtResponse(accessToken, user.getName(), user.getEmail());
        return new AuthResult(accessToken, refreshToken, jwtResponse);
    }

    public User registerUser(UserDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user.setName(dto.getName());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setBirthDate(dto.getBirthDate());
        user.setGender(dto.getGender());
        user.setHeight(dto.getHeight());
        user.setWeight(dto.getWeight());

        return userRepository.save(user);
    }
}

