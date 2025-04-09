package com.fitnessai.bodyanalyzer.repository;


import com.fitnessai.bodyanalyzer.domain.ExercisePlan;
import com.fitnessai.bodyanalyzer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExercisePlanRepository extends JpaRepository<ExercisePlan, Long> {
    List<ExercisePlan> findByUser(User user);
}