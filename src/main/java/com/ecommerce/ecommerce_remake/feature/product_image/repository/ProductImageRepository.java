package com.ecommerce.ecommerce_remake.feature.product_image.repository;

import com.ecommerce.ecommerce_remake.feature.product_image.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
}
