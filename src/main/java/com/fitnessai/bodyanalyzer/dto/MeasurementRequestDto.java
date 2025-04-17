package com.fitnessai.bodyanalyzer.dto;

import lombok.Data;

@Data
public class MeasurementRequestDto {
    private Long userId;
    private String direction;
    private String keypoints;
    private Float score;
}
