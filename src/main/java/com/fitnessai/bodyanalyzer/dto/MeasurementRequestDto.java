package com.fitnessai.bodyanalyzer.dto;

import com.fitnessai.bodyanalyzer.enums.ShotDirection;
import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class MeasurementRequestDto {
    private Long userId;
    private ShotDirection direction;
    private String keypoints;     // JSON 문자열
    private String guideImageUrl; // (선택)
    private Integer countdown;    // (선택)
}

