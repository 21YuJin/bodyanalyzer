package com.fitnessai.bodyanalyzer.repository;

import com.fitnessai.bodyanalyzer.domain.Measurement;
import com.fitnessai.bodyanalyzer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    List<Measurement> findByUser(User user);
    Optional<Measurement> findTopByUserIdOrderByMeasuredAtDesc(Long userId);
    List<Measurement> findByUserId(Long userId);
}
