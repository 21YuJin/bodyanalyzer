package com.fitnessai.bodyanalyzer.repository;

import com.fitnessai.bodyanalyzer.domain.History;
import com.fitnessai.bodyanalyzer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    // 유저 ID 기준으로 히스토리 조회
    List<History> findByUserId(Long userId);
    List<History> findByUserIdOrderByRecordedAtAsc(Long userId);
}