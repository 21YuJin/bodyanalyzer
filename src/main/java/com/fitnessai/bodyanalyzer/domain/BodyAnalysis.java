package com.fitnessai.bodyanalyzer.domain;

import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "body_analysis")
@Setter
public class BodyAnalysis {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long analysisId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "json")
    private String keypoints;

    private Float bodyTilt;
    private Float headRotation;
    private Float pelvisTilt;
    private LocalDateTime analysisDate = LocalDateTime.now();
}