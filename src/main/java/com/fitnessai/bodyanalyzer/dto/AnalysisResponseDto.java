package com.fitnessai.bodyanalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AnalysisResponseDto {
    private Long analysisId;
    private String keypoints;
    private Float bodyTilt;
    private Float headRotation;
    private Float pelvisTilt;
    private LocalDateTime analysisDate;
}
