package com.fitnessai.bodyanalyzer.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExerciseRecommendationDto {
    private Long userId;
    private Map<String, Integer> levels; // 예: {"거북목": 7, "어깨 비대칭": 4, ...}
}
