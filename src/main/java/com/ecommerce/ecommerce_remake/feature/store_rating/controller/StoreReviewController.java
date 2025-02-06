package com.ecommerce.ecommerce_remake.feature.store_rating.controller;

import com.ecommerce.ecommerce_remake.feature.product_review.dto.RateRequest;
import com.ecommerce.ecommerce_remake.feature.store_rating.service.StoreRatingService;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import com.ecommerce.ecommerce_remake.web.exception.InvalidRatingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
@Slf4j
public class StoreReviewController {
    private final StoreRatingService storeRatingService;
    private final UserService userService;
    @PostMapping("/{storeId}/{orderId}/rate")
    public void rateStore(@RequestBody RateRequest request, @PathVariable("storeId") Integer storeId, @PathVariable("orderId") Integer orderId) {
        Integer userId = userService.getUserId();
        log.info("Rate Store - StoreId: {}, UserId: {}, OrderId: {} Rating: {} ", storeId, userId, orderId, request.getRating());

        if(request.getRating() < 1 || request.getRating() > 5){
            log.error("Rate Store - Invalid rating received: {} for store ID={}", request.getRating(), storeId);
            throw new InvalidRatingException();
        }

        storeRatingService.rateStore(request, storeId, orderId, userId);
    }
}
