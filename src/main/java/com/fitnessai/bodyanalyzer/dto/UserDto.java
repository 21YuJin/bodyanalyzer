package com.fitnessai.bodyanalyzer.dto;

import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String password;

    private String name;
    private String phoneNumber;
    private String birthDate;
    private String gender;
    private Float height;
    private Float weight;
}
