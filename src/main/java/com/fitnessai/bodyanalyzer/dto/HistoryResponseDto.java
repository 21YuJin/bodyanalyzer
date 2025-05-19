package com.fitnessai.bodyanalyzer.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class HistoryResponseDto {
    private Long historyId;
    private Long userId;
    private Long measurementId;
    private String progressNotes;
    private Integer score;
    private String trend;
    private LocalDateTime recordedAt;
}
