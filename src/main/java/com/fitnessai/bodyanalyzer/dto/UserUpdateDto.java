package com.fitnessai.bodyanalyzer.dto;

import lombok.Data;

@Data
public class UserUpdateDto {
    private String name;
    private String email;
    private String phoneNumber;
    private String birthDate;
    private String gender;
    private Float height;
    private Float weight;
    private String password;
}
