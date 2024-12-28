package com.ecommerce.ecommerce_remake.feature.product.repository;

import com.ecommerce.ecommerce_remake.feature.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByProductName(String name);
}
