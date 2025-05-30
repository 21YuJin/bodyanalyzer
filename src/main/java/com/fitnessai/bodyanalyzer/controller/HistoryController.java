package com.fitnessai.bodyanalyzer.controller;

import com.fitnessai.bodyanalyzer.dto.HistoryRequestDto;
import com.fitnessai.bodyanalyzer.dto.HistoryResponseDto;
import com.fitnessai.bodyanalyzer.domain.User;
import com.fitnessai.bodyanalyzer.service.HistoryService;
import com.fitnessai.bodyanalyzer.service.UserService;
import com.fitnessai.bodyanalyzer.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<HistoryResponseDto> saveHistory(@RequestBody HistoryRequestDto dto) {
        return ResponseEntity.ok(historyService.saveHistory(dto));
    }

    @GetMapping("/me")
    public ResponseEntity<List<HistoryResponseDto>> getMyHistory(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        return ResponseEntity.ok(historyService.getUserHistory(user.getId()));
    }

}
