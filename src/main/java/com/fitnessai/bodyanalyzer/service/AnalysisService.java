package com.fitnessai.bodyanalyzer.service;

import com.fitnessai.bodyanalyzer.domain.BodyAnalysis;
import com.fitnessai.bodyanalyzer.domain.User;
import com.fitnessai.bodyanalyzer.dto.AnalysisRequestDto;
import com.fitnessai.bodyanalyzer.dto.AnalysisResponseDto;
import com.fitnessai.bodyanalyzer.repository.BodyAnalysisRepository;
import com.fitnessai.bodyanalyzer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<AnalysisResponseDto> getAnalysesByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return bodyAnalysisRepository.findByUser(user).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public AnalysisResponseDto getByAnalysisId(Long analysisId) {
        BodyAnalysis analysis = bodyAnalysisRepository.findById(analysisId).orElseThrow();
        return convertToDto(analysis);
    }

    public void deleteById(Long analysisId) {
        bodyAnalysisRepository.deleteById(analysisId);
    }

    private AnalysisResponseDto convertToDto(BodyAnalysis a) {
        return new AnalysisResponseDto(
                a.getId(),
                a.getKeypoints(),
                a.getBodyTilt(),
                a.getHeadRotation(),
                a.getPelvisTilt(),
                a.getAnalysisDate()
        );
    }
}
