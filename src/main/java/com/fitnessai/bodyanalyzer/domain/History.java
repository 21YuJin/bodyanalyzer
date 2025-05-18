package com.fitnessai.bodyanalyzer.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "history")
public class History {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "measurement_id", nullable = false)
    private Measurement measurement;

    private String progressNotes;
    private LocalDateTime recordedAt = LocalDateTime.now();
}