package com.fitnessai.bodyanalyzer.service;

import com.fitnessai.bodyanalyzer.domain.BodyAnalysis;
import com.fitnessai.bodyanalyzer.domain.User;
import com.fitnessai.bodyanalyzer.dto.AnalysisRequestDto;
import com.fitnessai.bodyanalyzer.repository.BodyAnalysisRepository;
import com.fitnessai.bodyanalyzer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalysisService {
    private final BodyAnalysisRepository bodyAnalysisRepository;
    private final UserRepository userRepository;

    public BodyAnalysis saveAnalysis(AnalysisRequestDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        BodyAnalysis analysis = new BodyAnalysis();
        analysis.setUser(user);
        analysis.setKeypoints(dto.getKeypoints());
        analysis.setBodyTilt(dto.getBodyTilt());
        analysis.setHeadRotation(dto.getHeadRotation());
        analysis.setPelvisTilt(dto.getPelvisTilt());
        return bodyAnalysisRepository.save(analysis);
    }
}