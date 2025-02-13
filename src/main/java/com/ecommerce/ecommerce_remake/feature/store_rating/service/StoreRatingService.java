package com.ecommerce.ecommerce_remake.feature.store_rating.service;

import com.ecommerce.ecommerce_remake.feature.product_review.dto.RateRequest;
import com.ecommerce.ecommerce_remake.feature.store_rating.model.StoreRating;

import java.util.Optional;

public interface StoreRatingService {

    void rateStore(RateRequest request, Integer storeId, Integer orderId, Integer userId);
    Optional<StoreRating> findIfUserAlreadyRatedStore(Integer userId, Integer storeId);

}
