package com.fitnessai.bodyanalyzer.domain;

import com.fitnessai.bodyanalyzer.enums.ShotDirection;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "measurements")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long measurementId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShotDirection direction;

    @Column(columnDefinition = "json")
    private String keypoints;

    private LocalDateTime measuredAt = LocalDateTime.now();
}
