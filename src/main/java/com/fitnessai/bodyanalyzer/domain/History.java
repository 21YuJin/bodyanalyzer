package com.fitnessai.bodyanalyzer.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "history")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "measurement_id", nullable = false)
    private Measurement measurement;

    private String progressNotes;

    private Integer score; // 체형 점수 (예: 80/100)
    private String trend;  // "개선", "유지", "악화"

    private LocalDateTime recordedAt = LocalDateTime.now();
}
