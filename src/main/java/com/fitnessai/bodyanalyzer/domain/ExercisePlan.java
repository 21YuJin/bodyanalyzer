package com.fitnessai.bodyanalyzer.domain;

import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Entity
@Table(name = "exercise_plan")
public class ExercisePlan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "text")
    private String recommendedExercises;

    private LocalDateTime createdAt = LocalDateTime.now();
}

