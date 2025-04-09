package com.fitnessai.bodyanalyzer.controller;

import com.fitnessai.bodyanalyzer.domain.User;
import com.fitnessai.bodyanalyzer.dto.UserDto;
import com.fitnessai.bodyanalyzer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDto dto) {
        User user = userService.registerUser(dto);
        return ResponseEntity.ok(user);
    }
}