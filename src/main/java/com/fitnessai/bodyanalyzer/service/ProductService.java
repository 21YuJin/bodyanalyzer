package com.fitnessai.bodyanalyzer.service;

import com.fitnessai.bodyanalyzer.domain.Product;
import com.fitnessai.bodyanalyzer.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
