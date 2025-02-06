package com.ecommerce.ecommerce_remake.feature.store_review.controller;

import com.ecommerce.ecommerce_remake.feature.product_review.dto.RateRequest;
import com.ecommerce.ecommerce_remake.feature.store_review.service.StoreReviewService;
import com.ecommerce.ecommerce_remake.web.exception.InvalidRatingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
@Slf4j
public class StoreReviewController {
    private final StoreReviewService storeReviewService;
    @PostMapping("/{storeId}/{orderId}/rate")
    public void rateStore(@RequestBody RateRequest request, @PathVariable("storeId") Integer storeId, @PathVariable("orderId") Integer orderId) {
        log.info("Rate Store - StoreId: {}, OrderId: {} Rating: {} ", storeId, orderId, request.getRating());

        if(request.getRating() < 1 || request.getRating() > 5){
            log.error("Rate Store - Invalid rating received: {} for store ID={}", request.getRating(), storeId);
            throw new InvalidRatingException();
        }

        storeReviewService.rateStore(request, storeId, orderId);
    }
}
