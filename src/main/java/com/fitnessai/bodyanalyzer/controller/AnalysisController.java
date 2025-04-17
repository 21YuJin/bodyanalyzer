package com.fitnessai.bodyanalyzer.controller;

import com.fitnessai.bodyanalyzer.domain.BodyAnalysis;
import com.fitnessai.bodyanalyzer.dto.AnalysisRequestDto;
import com.fitnessai.bodyanalyzer.dto.AnalysisResponseDto;
import com.fitnessai.bodyanalyzer.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{analysisId}")
    public ResponseEntity<AnalysisResponseDto> getById(@PathVariable Long analysisId) {
        AnalysisResponseDto result = analysisService.getByAnalysisId(analysisId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{analysisId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long analysisId) {
        analysisService.deleteById(analysisId);
        return ResponseEntity.noContent().build();
    }


}
