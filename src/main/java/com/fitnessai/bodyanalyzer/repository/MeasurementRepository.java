package com.fitnessai.bodyanalyzer.repository;

import com.fitnessai.bodyanalyzer.domain.Measurement;
import com.fitnessai.bodyanalyzer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    List<Measurement> findByUser(User user);
}
