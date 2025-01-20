package com.ecommerce.ecommerce_remake.feature.product_review.repository;

import com.ecommerce.ecommerce_remake.feature.product_review.model.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer> {
}
