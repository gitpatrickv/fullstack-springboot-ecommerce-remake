package com.ecommerce.ecommerce_remake.feature.product_review.controller;

import com.ecommerce.ecommerce_remake.feature.product_review.dto.RateRequest;
import com.ecommerce.ecommerce_remake.feature.product_review.service.ProductReviewService;
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
    @PostMapping
    public void rateProduct(@RequestBody RateRequest request){
        productReviewService.rateProduct(request);
    }
}
