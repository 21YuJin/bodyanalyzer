package com.fitnessai.bodyanalyzer.repository;

import com.fitnessai.bodyanalyzer.domain.BodyAnalysis;
import com.fitnessai.bodyanalyzer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BodyAnalysisRepository extends JpaRepository<BodyAnalysis, Long> {
    List<BodyAnalysis> findByUser(User user);
}