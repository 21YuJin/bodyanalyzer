package com.fitnessai.bodyanalyzer.repository;

import com.fitnessai.bodyanalyzer.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
