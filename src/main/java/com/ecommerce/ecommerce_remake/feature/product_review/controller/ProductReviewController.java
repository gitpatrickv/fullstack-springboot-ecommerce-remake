package com.ecommerce.ecommerce_remake.feature.product_review.controller;

import com.ecommerce.ecommerce_remake.feature.product_review.dto.RateRequest;
import com.ecommerce.ecommerce_remake.feature.product_review.service.ProductReviewService;
import com.ecommerce.ecommerce_remake.web.exception.InvalidRatingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductReviewController {

    private final ProductReviewService productReviewService;
    @PostMapping("/rate")
    public void rateProduct(@RequestBody @Valid RateRequest request){
        log.info("Received request to Rate Product for ID={}", request.getProductId());

        if(request.getRating() < 1 || request.getRating() > 5){
            log.error("Rate Product - Invalid rating received: {} for product ID={}", request.getRating(), request.getProductId());
            throw new InvalidRatingException();
        }

       productReviewService.rateProduct(request);
    }
}
