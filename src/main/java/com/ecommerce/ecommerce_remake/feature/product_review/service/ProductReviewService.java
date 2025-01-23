package com.ecommerce.ecommerce_remake.feature.product_review.service;

import com.ecommerce.ecommerce_remake.feature.product_review.dto.RateRequest;
import com.ecommerce.ecommerce_remake.feature.product_review.model.ProductReview;

import java.util.Optional;

public interface ProductReviewService {

    void rateProduct(RateRequest request);
    Optional<ProductReview> findReviewByUserAndProduct(Integer userId, Integer productId);
}
