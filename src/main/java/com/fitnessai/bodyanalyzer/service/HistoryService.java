package com.fitnessai.bodyanalyzer.service;

import com.fitnessai.bodyanalyzer.domain.History;
import com.fitnessai.bodyanalyzer.domain.Measurement;
import com.fitnessai.bodyanalyzer.domain.User;
import com.fitnessai.bodyanalyzer.dto.HistoryRequestDto;
import com.fitnessai.bodyanalyzer.dto.HistoryResponseDto;
import com.fitnessai.bodyanalyzer.repository.HistoryRepository;
import com.fitnessai.bodyanalyzer.repository.MeasurementRepository;
import com.fitnessai.bodyanalyzer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;
    private final MeasurementRepository measurementRepository;

    public HistoryResponseDto saveHistory(HistoryRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Measurement measurement = measurementRepository.findById(dto.getMeasurementId())
                .orElseThrow(() -> new IllegalArgumentException("Measurement not found"));

        History history = new History();
        history.setUser(user);
        history.setMeasurement(measurement);
        history.setProgressNotes(dto.getProgressNotes());
        history.setScore(dto.getScore());
        history.setTrend(dto.getTrend());

        History saved = historyRepository.save(history);
        return convertToDto(saved);
    }

    public List<HistoryResponseDto> getUserHistory(Long userId) {
        return historyRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private HistoryResponseDto convertToDto(History history) {
        return HistoryResponseDto.builder()
                .historyId(history.getId())
                .userId(history.getUser().getId())
                .measurementId(history.getMeasurement().getId())
                .progressNotes(history.getProgressNotes())
                .score(history.getScore())
                .trend(history.getTrend())
                .recordedAt(history.getRecordedAt())
                .direction(history.getMeasurement().getDirection().name())
                .build();
    }
}
