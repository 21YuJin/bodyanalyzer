package com.fitnessai.bodyanalyzer.service;

import com.fitnessai.bodyanalyzer.domain.ExercisePlan;
import com.fitnessai.bodyanalyzer.domain.User;
import com.fitnessai.bodyanalyzer.repository.ExercisePlanRepository;
import com.fitnessai.bodyanalyzer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExercisePlanRepository exercisePlanRepository;
    private final UserRepository userRepository;

    public ExercisePlan createPlan(Long userId, String exercisesJson) {
        User user = userRepository.findById(userId).orElseThrow();
        ExercisePlan plan = new ExercisePlan();
        plan.setUser(user);
        plan.setRecommendedExercises(exercisesJson);
        return exercisePlanRepository.save(plan);
    }
}