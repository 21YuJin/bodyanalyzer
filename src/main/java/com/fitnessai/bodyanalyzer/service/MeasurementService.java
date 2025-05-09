package com.fitnessai.bodyanalyzer.service;

import com.fitnessai.bodyanalyzer.domain.Measurement;
import com.fitnessai.bodyanalyzer.domain.User;
import com.fitnessai.bodyanalyzer.dto.MeasurementRequestDto;
import com.fitnessai.bodyanalyzer.repository.MeasurementRepository;
import com.fitnessai.bodyanalyzer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final UserRepository userRepository;

    public Measurement saveMeasurement(MeasurementRequestDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        Measurement measurement = new Measurement();
        measurement.setUser(user);
        measurement.setDirection(dto.getDirection());
        measurement.setKeypoints(dto.getKeypoints());
        return measurementRepository.save(measurement);
    }
}
