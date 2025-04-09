package com.fitnessai.bodyanalyzer.dto;

import lombok.Data;

@Data
public class AnalysisRequestDto {
    private Long userId;
    private String keypoints;
    private Float bodyTilt;
    private Float headRotation;
    private Float pelvisTilt;
}