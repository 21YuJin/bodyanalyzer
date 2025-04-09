package com.fitnessai.bodyanalyzer.repository;

import com.fitnessai.bodyanalyzer.domain.History;
import com.fitnessai.bodyanalyzer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByUser(User user);
}