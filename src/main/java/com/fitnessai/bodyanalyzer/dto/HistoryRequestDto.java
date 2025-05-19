package com.fitnessai.bodyanalyzer.dto;

import lombok.Data;

@Data
public class HistoryRequestDto {
    private Long userId;
    private Long measurementId;
    private String progressNotes;
    private Integer score;   // 추가
    private String trend;    // 추가
}
