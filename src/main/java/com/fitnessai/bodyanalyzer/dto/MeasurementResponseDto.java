package com.fitnessai.bodyanalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class MeasurementResponseDto {
    private Long userId;
    private Map<String, Integer> levels;  // 예: {"거북목": 4, "골반 전방경사": 2}
    private Map<String, String> descriptions; // 예: {"거북목": "초기", "골반 전방경사": "매우 경미"}
}
