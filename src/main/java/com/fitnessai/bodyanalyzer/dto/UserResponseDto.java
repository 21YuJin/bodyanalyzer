package com.fitnessai.bodyanalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String email;
    private String name;
    private String phoneNumber;
    private String birthDate;
    private String gender;
    private Float height;
    private Float weight;
    private LocalDateTime createdAt;
}
