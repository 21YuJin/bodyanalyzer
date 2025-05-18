package com.fitnessai.bodyanalyzer.controller;

import com.fitnessai.bodyanalyzer.domain.Measurement;
import com.fitnessai.bodyanalyzer.dto.MeasurementRequestDto;
import com.fitnessai.bodyanalyzer.dto.MeasurementResponseDto;
import com.fitnessai.bodyanalyzer.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/measurements")
@RequiredArgsConstructor
public class MeasurementController {

    private final MeasurementService measurementService;

    @PostMapping("/analyze")
    public ResponseEntity<MeasurementResponseDto> analyzeAndSave(@RequestBody MeasurementRequestDto dto) {
        Measurement saved = measurementService.saveMeasurement(dto);  // 저장
        MeasurementResponseDto result = measurementService.analyzeMeasurement(dto); // 분석
        return ResponseEntity.ok(result);
    }

}
