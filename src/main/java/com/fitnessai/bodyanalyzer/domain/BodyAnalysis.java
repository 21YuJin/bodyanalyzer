package com.fitnessai.bodyanalyzer.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "body_analysis")
@Setter
@Getter
public class BodyAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "analysis_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "json")
    private String keypoints;

    private Float bodyTilt; // 몸 기울기
    private Float headRotation; // 머리 각도
    private Float pelvisTilt; // 골반 경사
    private LocalDateTime analysisDate = LocalDateTime.now();
}