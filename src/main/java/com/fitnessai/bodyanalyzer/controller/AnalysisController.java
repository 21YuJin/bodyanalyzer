package com.fitnessai.bodyanalyzer.controller;

import com.fitnessai.bodyanalyzer.domain.BodyAnalysis;
import com.fitnessai.bodyanalyzer.dto.AnalysisRequestDto;
import com.fitnessai.bodyanalyzer.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisController {
    private final AnalysisService analysisService;

    @PostMapping("/save")
    public ResponseEntity<BodyAnalysis> save(@RequestBody AnalysisRequestDto dto) {
        BodyAnalysis analysis = analysisService.saveAnalysis(dto);
        return ResponseEntity.ok(analysis);
    }
}
