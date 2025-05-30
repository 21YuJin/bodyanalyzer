package com.fitnessai.bodyanalyzer.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderResponseDto {
    private Long orderId;
    private String productName;
    private int quantity;
    private int totalPrice;
    private LocalDateTime orderDate;
}
