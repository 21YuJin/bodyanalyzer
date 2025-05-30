package com.fitnessai.bodyanalyzer.service;

import com.fitnessai.bodyanalyzer.domain.Order;
import com.fitnessai.bodyanalyzer.domain.Product;
import com.fitnessai.bodyanalyzer.domain.User;
import com.fitnessai.bodyanalyzer.dto.OrderRequestDto;
import com.fitnessai.bodyanalyzer.dto.OrderResponseDto;
import com.fitnessai.bodyanalyzer.repository.OrderRepository;
import com.fitnessai.bodyanalyzer.repository.ProductRepository;
import com.fitnessai.bodyanalyzer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Order order = new Order();
        order.setUser(user);
        order.setProduct(product);
        order.setQuantity(requestDto.getQuantity());

        Order savedOrder = orderRepository.save(order);

        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setOrderId(savedOrder.getId());
        responseDto.setProductName(product.getName());
        responseDto.setQuantity(savedOrder.getQuantity());
        responseDto.setTotalPrice(product.getPrice() * savedOrder.getQuantity());
        responseDto.setOrderDate(savedOrder.getOrderDate());

        return responseDto;
    }
}
