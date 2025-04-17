package com.fitnessai.bodyanalyzer.controller;

import com.fitnessai.bodyanalyzer.domain.Measurement;
import com.fitnessai.bodyanalyzer.dto.MeasurementRequestDto;
import com.fitnessai.bodyanalyzer.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/measurements")
@RequiredArgsConstructor
public class MeasurementController {

    private final MeasurementService measurementService;

    @PostMapping("/save")
    public ResponseEntity<Measurement> saveMeasurement(@RequestBody MeasurementRequestDto dto) {
        return ResponseEntity.ok(measurementService.saveMeasurement(dto));
    }
}
