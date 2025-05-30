package com.fitnessai.bodyanalyzer.controller;

import com.fitnessai.bodyanalyzer.dto.OrderRequestDto;
import com.fitnessai.bodyanalyzer.dto.OrderResponseDto;
import com.fitnessai.bodyanalyzer.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> placeOrder(@RequestBody OrderRequestDto requestDto) {
        OrderResponseDto responseDto = orderService.createOrder(requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
