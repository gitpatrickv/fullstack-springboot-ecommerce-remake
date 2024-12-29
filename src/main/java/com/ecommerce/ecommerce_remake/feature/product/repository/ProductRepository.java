package com.ecommerce.ecommerce_remake.feature.product.repository;

import com.ecommerce.ecommerce_remake.feature.product.enums.ProductStatus;
import com.ecommerce.ecommerce_remake.feature.product.model.Product;
import com.ecommerce.ecommerce_remake.feature.store.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByProductName(String name);
    Page<Product> findByStore(Store store, Pageable pageable);
    Page<Product> findAllByProductStatus(ProductStatus status, Pageable pageable);
}
