package com.fitnessai.bodyanalyzer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
    private Long userId;
    private Long productId;
    private int quantity;
}
