package com.ecommerce.ecommerce_remake.feature.product_review.controller;

import com.ecommerce.ecommerce_remake.feature.product_review.dto.RateRequest;
import com.ecommerce.ecommerce_remake.feature.product_review.dto.RatingCount;
import com.ecommerce.ecommerce_remake.feature.product_review.service.ProductReviewService;
import com.ecommerce.ecommerce_remake.web.exception.InvalidRatingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductReviewController {

    private final ProductReviewService productReviewService;
    @PostMapping("/{productId}/{orderId}/rate")
    public void rateProduct(@RequestBody RateRequest request,
                            @PathVariable("productId") Integer productId,
                            @PathVariable("orderId") Integer orderId){

        log.info("Received request to Rate Product for ID={}", productId);
        if(request.getRating() < 1 || request.getRating() > 5){
            log.error("Rate Product - Invalid rating received: {} for product ID={}", request.getRating(), productId);
            throw new InvalidRatingException();
        }

       productReviewService.rateProduct(request, productId, orderId);
    }

    @GetMapping("/{productId}/rating-count")
    public ResponseEntity<RatingCount> getProductRatingStarCount(@PathVariable("productId") Integer productId){
        RatingCount ratingCount =  productReviewService.getProductRatingStarCount(productId);
        log.info("Product Id={}, {} ", productId, ratingCount);
        return ResponseEntity.ok().body(ratingCount);
    }
}
