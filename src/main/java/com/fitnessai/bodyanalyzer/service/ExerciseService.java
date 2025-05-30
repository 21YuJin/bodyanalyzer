package com.fitnessai.bodyanalyzer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnessai.bodyanalyzer.domain.ExercisePlan;
import com.fitnessai.bodyanalyzer.domain.Measurement;
import com.fitnessai.bodyanalyzer.domain.User;
import com.fitnessai.bodyanalyzer.dto.ExerciseRecommendationDto;
import com.fitnessai.bodyanalyzer.dto.ExercisePlanResponseDto;
import com.fitnessai.bodyanalyzer.dto.MeasurementRequestDto;
import com.fitnessai.bodyanalyzer.dto.MeasurementResponseDto;
import com.fitnessai.bodyanalyzer.repository.ExercisePlanRepository;
import com.fitnessai.bodyanalyzer.repository.MeasurementRepository;
import com.fitnessai.bodyanalyzer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExercisePlanRepository exercisePlanRepository;
    private final UserRepository userRepository;
    private final MeasurementRepository measurementRepository;
    private final MeasurementService measurementService;

    public ExercisePlanResponseDto generatePlan(ExerciseRecommendationDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow();

        Map<String, Integer> levels = dto.getLevels();
        List<Map<String, Object>> exerciseList = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : levels.entrySet()) {
            String category = entry.getKey();
            int level = entry.getValue();

            exerciseList.add(getExerciseByLevel(category, level));
        }

        String exercisesJson = toJson(exerciseList);
        ExercisePlan plan = new ExercisePlan();
        plan.setUser(user);
        plan.setRecommendedExercises(exercisesJson);
        exercisePlanRepository.save(plan);

        return ExercisePlanResponseDto.builder()
                .userId(user.getId())
                .recommendedExercises(exercisesJson)
                .build();
    }

    public ExercisePlanResponseDto generatePlanFromLatestMeasurement(Long userId) {
        Measurement latest = measurementRepository.findTopByUserIdOrderByMeasuredAtDesc(userId)
                .orElseThrow(() -> new IllegalArgumentException("측정 데이터가 없습니다"));

        MeasurementRequestDto dto = MeasurementRequestDto.builder()
                .userId(userId)
                .direction(latest.getDirection())
                .keypoints(latest.getKeypoints())
                .countdown(latest.getCountdown())
                .guideImageUrl(latest.getGuideImageUrl())
                .build();

        MeasurementResponseDto analysis = measurementService.analyzeMeasurement(dto);

        ExerciseRecommendationDto recDto = ExerciseRecommendationDto.builder()
                .userId(userId)
                .levels(analysis.getLevels())
                .build();

        return generatePlan(recDto);
    }

    private Map<String, Object> getExerciseByLevel(String category, int level) {
        String 운동 = "";
        String 반복 = "";

        if (category.equals("거북목")) {
            if (level <= 3) { 운동 = "경부 스트레칭"; 반복 = "1세트 / 8~10회"; }
            else if (level <= 6) { 운동 = "어깨 안정화 운동"; 반복 = "2세트 / 10~12회"; }
            else { 운동 = "코어 강화 + 런지"; 반복 = "3세트 / 15회"; }
        } else if (category.equals("골반 전방경사")) {
            if (level <= 3) { 운동 = "상부승모 스트레칭"; 반복 = "1세트 / 10회"; }
            else if (level <= 6) { 운동 = "스트레칭 + 강화"; 반복 = "2세트 / 10~12회"; }
            else { 운동 = "흉추 신전 + 저항 밴드"; 반복 = "3세트 / 12~15회"; }
        } else if (category.equals("어깨 비대칭")) {
            if (level <= 3) { 운동 = "힙 어브덕션"; 반복 = "1세트 / 12회"; }
            else if (level <= 6) { 운동 = "밴드 리버스 플라이"; 반복 = "2세트 / 12회"; }
            else { 운동 = "스트레칭 + 집중 강화"; 반복 = "3세트 / 12~15회"; }
        } else if (category.equals("라운드 숄더")) {
            if (level <= 3) { 운동 = "햄스트링 스트레칭"; 반복 = "1세트 / 10회"; }
            else if (level <= 6) { 운동 = "사이드 스텝업"; 반복 = "2세트 / 15회"; }
            else { 운동 = "언밸런스 해소 운동"; 반복 = "3세트 / 15회"; }
        } else if (category.equals("무릎 내반")) {
            if (level <= 3) { 운동 = "가슴 스트레칭"; 반복 = "1세트 / 8~10회"; }
            else if (level <= 6) { 운동 = "둔근 활성화"; 반복 = "2세트 / 12회"; }
            else { 운동 = "밸런스 런지 + 밴드"; 반복 = "3세트 / 18회"; }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("항목", category);
        result.put("심각도", level);
        result.put("운동 구성", 운동);
        result.put("반복", 반복);
        return result;
    }

    private String toJson(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            return "{}";
        }
    }
}
