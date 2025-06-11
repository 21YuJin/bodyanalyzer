package com.fitnessai.bodyanalyzer.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnessai.bodyanalyzer.domain.Measurement;
import com.fitnessai.bodyanalyzer.domain.User;
import com.fitnessai.bodyanalyzer.dto.MeasurementRequestDto;
import com.fitnessai.bodyanalyzer.dto.MeasurementResponseDto;
import com.fitnessai.bodyanalyzer.dto.MeasurementSimpleResponseDto;
import com.fitnessai.bodyanalyzer.repository.MeasurementRepository;
import com.fitnessai.bodyanalyzer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Measurement saveMeasurement(MeasurementRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + dto.getUserId()));

        Measurement measurement = new Measurement();
        measurement.setUser(user);
        measurement.setDirection(dto.getDirection());
        measurement.setKeypoints(dto.getKeypoints());
        measurement.setGuideImageUrl(dto.getGuideImageUrl());
        measurement.setCountdown(dto.getCountdown());

        return measurementRepository.save(measurement);
    }

    public MeasurementResponseDto analyzeMeasurement(MeasurementRequestDto dto) {
        Map<String, double[]> keypoints = parseKeypoints(dto.getKeypoints());

        double neckAngle = angleBetweenPoints(keypoints.get("4"), keypoints.get("6"), keypoints.get("12")); // Ear-Shoulder-Hip
        double pelvisAngle = angleBetweenPoints(keypoints.get("6"), keypoints.get("12"), keypoints.get("14")); // Shoulder-Hip-Knee
        double shoulderDiff = Math.abs(keypoints.get("5")[1] - keypoints.get("6")[1]) / 10.0; // cm 환산용 (예시)
        double shoulderAngle = angleBetweenPoints(keypoints.get("6"), keypoints.get("4"), keypoints.get("0")); // Shoulder-Ear-Nose
        double kneeAngle = angleBetweenPoints(keypoints.get("12"), keypoints.get("14"), keypoints.get("16")); // Hip-Knee-Ankle

        Map<String, Integer> levels = new HashMap<>();
        Map<String, String> descriptions = new HashMap<>();

        levels.put("거북목", getLevel(neckAngle, "거북목"));
        descriptions.put("거북목", getDescription(levels.get("거북목"), "거북목"));

        levels.put("골반 전방경사", getLevel(pelvisAngle, "골반 전방경사"));
        descriptions.put("골반 전방경사", getDescription(levels.get("골반 전방경사"), "골반 전방경사"));

        levels.put("어깨 비대칭", getLevel(shoulderDiff, "어깨 비대칭"));
        descriptions.put("어깨 비대칭", getDescription(levels.get("어깨 비대칭"), "어깨 비대칭"));

        levels.put("라운드 숄더", getLevel(shoulderAngle, "라운드 숄더"));
        descriptions.put("라운드 숄더", getDescription(levels.get("라운드 숄더"), "라운드 숄더"));

        levels.put("무릎 내반", getLevel(kneeAngle, "무릎 내반"));
        descriptions.put("무릎 내반", getDescription(levels.get("무릎 내반"), "무릎 내반"));

        return MeasurementResponseDto.builder()
                .userId(dto.getUserId())
                .levels(levels)
                .descriptions(descriptions)
                .build();
    }

    private Map<String, double[]> parseKeypoints(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException("Invalid keypoints JSON", e);
        }
    }

    private double distance(double[] p1, double[] p2) {
        return Math.sqrt(Math.pow(p1[0] - p2[0], 2) + Math.pow(p1[1] - p2[1], 2));
    }

    private double angleBetweenPoints(double[] A, double[] B, double[] C) {
        double ab = distance(A, B);
        double bc = distance(B, C);
        double ac = distance(A, C);
        try {
            double cos = (ab * ab + bc * bc - ac * ac) / (2 * ab * bc);
            cos = Math.max(-1.0, Math.min(1.0, cos));
            return Math.toDegrees(Math.acos(cos));
        } catch (Exception e) {
            return 0.0;
        }
    }

    private int getLevel(double value, String type) {
        switch (type) {
            case "거북목":
            case "라운드 숄더":
                if (value <= 5) return 1;
                else if (value <= 8) return 2;
                else if (value <= 11) return 3;
                else if (value <= 14) return 4;
                else if (value <= 18) return 5;
                else if (value <= 22) return 6;
                else if (value <= 26) return 7;
                else if (value <= 30) return 8;
                else if (value <= 35) return 9;
                else return 10;

            case "골반 전방경사":
                if (value <= 3) return 1;
                else if (value <= 5) return 2;
                else if (value <= 7) return 3;
                else if (value <= 9) return 4;
                else if (value <= 12) return 5;
                else if (value <= 15) return 6;
                else if (value <= 18) return 7;
                else if (value <= 22) return 8;
                else if (value <= 25) return 9;
                else return 10;

            case "어깨 비대칭":
                if (value <= 0.2) return 1;
                else if (value <= 0.5) return 2;
                else if (value <= 0.9) return 3;
                else if (value <= 1.3) return 4;
                else if (value <= 1.7) return 5;
                else if (value <= 2.2) return 6;
                else if (value <= 2.6) return 7;
                else if (value <= 3.0) return 8;
                else if (value <= 3.5) return 9;
                else return 10;

            case "무릎 내반":
                if (value <= 2) return 1;
                else if (value <= 4) return 2;
                else if (value <= 6) return 3;
                else if (value <= 8) return 4;
                else if (value <= 10) return 5;
                else if (value <= 12) return 6;
                else if (value <= 14) return 7;
                else if (value <= 16) return 8;
                else if (value <= 18) return 9;
                else return 10;

            default:
                return 1;
        }
    }

    private String getDescription(int level, String type) {
        String[] desc = {
                "", "정상", "매우 경미", "경미", "초기", "이상 징후",
                "중등도", "진행성", "고도 이상", "매우 고도", "병적 수준"
        };
        return desc[level];
    }

    public List<Measurement> getMeasurementsByUserId(Long userId) {
        return measurementRepository.findByUserId(userId);
    }

    public List<MeasurementSimpleResponseDto> getSimpleMeasurementsByUserId(Long userId) {
        return measurementRepository.findByUserId(userId).stream()
                .map(m -> MeasurementSimpleResponseDto.builder()
                        .id(m.getId())
                        .direction(m.getDirection())
                        .guideImageUrl(m.getGuideImageUrl())
                        .countdown(m.getCountdown())
                        .measuredAt(m.getMeasuredAt())
                        .build())
                .toList();
    }


}
