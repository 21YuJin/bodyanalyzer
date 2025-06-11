package com.fitnessai.bodyanalyzer.controller;

import com.fitnessai.bodyanalyzer.domain.Measurement;
import com.fitnessai.bodyanalyzer.dto.MeasurementRequestDto;
import com.fitnessai.bodyanalyzer.dto.MeasurementResponseDto;
import com.fitnessai.bodyanalyzer.dto.MeasurementSimpleResponseDto;
import com.fitnessai.bodyanalyzer.security.CustomUserDetails;
import com.fitnessai.bodyanalyzer.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/measurements")
@RequiredArgsConstructor
public class MeasurementController {

    private final MeasurementService measurementService;

    @PostMapping("/analyze")
    public ResponseEntity<MeasurementResponseDto> analyzeAndSave(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody MeasurementRequestDto dto) {

        Long userId = userDetails.getUser().getId();
        dto.setUserId(userId); // 인증된 사용자 ID를 DTO에 주입

        Measurement saved = measurementService.saveMeasurement(dto);  // 저장
        MeasurementResponseDto result = measurementService.analyzeMeasurement(dto); // 분석
        return ResponseEntity.ok(result);
    }

    @GetMapping("/me")
    public ResponseEntity<List<MeasurementSimpleResponseDto>> getMyMeasurements(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUser().getId();
        List<MeasurementSimpleResponseDto> result = measurementService.getSimpleMeasurementsByUserId(userId);
        return ResponseEntity.ok(result);
    }

}
