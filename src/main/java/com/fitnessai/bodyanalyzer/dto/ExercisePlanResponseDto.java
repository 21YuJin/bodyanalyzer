package com.fitnessai.bodyanalyzer.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExercisePlanResponseDto {
    private Long userId;
    private String recommendedExercises; // JSON 또는 문자열 형식
}
