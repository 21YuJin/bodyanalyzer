package com.fitnessai.bodyanalyzer.dto;

import com.fitnessai.bodyanalyzer.enums.ShotDirection;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MeasurementSimpleResponseDto {
    private Long id;
    private ShotDirection direction;
    private String guideImageUrl;
    private Integer countdown;
    private LocalDateTime measuredAt;
}
