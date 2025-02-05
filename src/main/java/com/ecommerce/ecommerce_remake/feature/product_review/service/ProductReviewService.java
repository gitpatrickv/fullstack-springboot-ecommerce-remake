package com.ecommerce.ecommerce_remake.feature.product_review.service;

import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.feature.order.model.Order;
import com.ecommerce.ecommerce_remake.feature.product_review.dto.RateRequest;
import com.ecommerce.ecommerce_remake.feature.product_review.dto.RatingCount;
import com.ecommerce.ecommerce_remake.feature.product_review.model.ProductReview;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductReviewService {

    void rateProduct(RateRequest request, Integer productId, Integer orderId, Integer storeId);
    Optional<ProductReview> findReviewByUserAndProduct(Integer userId, Integer productId);
    void updateOrderStatus(Order order);
    RatingCount getProductRatingStarCount(String productId);
    GetAllResponse getProductReviews(String productId, Integer rating, Pageable pageable);
    GetAllResponse getProductReviewsByStore(Integer storeId, Pageable pageable);
}
