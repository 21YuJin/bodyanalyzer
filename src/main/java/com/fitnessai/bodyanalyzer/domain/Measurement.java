package com.fitnessai.bodyanalyzer.domain;

import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Table(name = "measurements")
public class Measurement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long measurementId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String direction;

    @Column(columnDefinition = "json")
    private String keypoints;

    private Float score;

    private LocalDateTime measuredAt = LocalDateTime.now();
}