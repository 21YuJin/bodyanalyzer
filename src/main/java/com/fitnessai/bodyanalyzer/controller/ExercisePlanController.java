package com.fitnessai.bodyanalyzer.controller;

import com.fitnessai.bodyanalyzer.dto.ExerciseRecommendationDto;
import com.fitnessai.bodyanalyzer.dto.ExercisePlanResponseDto;
import com.fitnessai.bodyanalyzer.security.CustomUserDetails;
import com.fitnessai.bodyanalyzer.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exercise")
public class ExercisePlanController {

    private final ExerciseService exerciseService;

    @PostMapping("/recommend")
    public ExercisePlanResponseDto recommendByToken(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUser().getUserId();
        return exerciseService.generatePlanFromLatestMeasurement(userId);
    }

}
