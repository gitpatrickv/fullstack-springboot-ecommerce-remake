package com.ecommerce.ecommerce_remake.feature.store_review.service;

import com.ecommerce.ecommerce_remake.feature.order.model.Order;
import com.ecommerce.ecommerce_remake.feature.product_review.dto.RateRequest;
import com.ecommerce.ecommerce_remake.feature.store_review.model.StoreReview;

import java.util.Optional;

public interface StoreReviewService {

    void rateStore(RateRequest request, Integer storeId, Integer orderId);
    Optional<StoreReview> findIfUserAlreadyRatedStore(Integer userId, Integer storeId);
    void updateOrderIfUserAlreadyRatedStore(Order order);
}
