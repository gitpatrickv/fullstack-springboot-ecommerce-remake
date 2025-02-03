package com.ecommerce.ecommerce_remake.feature.product_review.controller;

import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.feature.product_review.dto.RateRequest;
import com.ecommerce.ecommerce_remake.feature.product_review.dto.RatingCount;
import com.ecommerce.ecommerce_remake.feature.product_review.service.ProductReviewService;
import com.ecommerce.ecommerce_remake.web.exception.InvalidRatingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ecommerce.ecommerce_remake.common.util.PageableUtils.createPaginationAndSorting;

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
    public ResponseEntity<RatingCount> getProductRatingStarCount(@PathVariable("productId") String productId){
        RatingCount ratingCount =  productReviewService.getProductRatingStarCount(productId);
        log.info("Product Id={}, {} ", productId, ratingCount);
        return ResponseEntity.ok().body(ratingCount);
    }

    @GetMapping("/{productId}/reviews")
    public ResponseEntity<GetAllResponse> getProductReviews(@PathVariable("productId") String productId,
                                                            @RequestParam(value = "rating", required = false) Integer rating,
                                                            @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                                            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                            @RequestParam(value = "sortBy", defaultValue = "createdDate") String sortBy,
                                                            @RequestParam(value = "sortDirection", defaultValue = "DESC") String sortDirection){
        Pageable pageable = createPaginationAndSorting(pageNo, pageSize, sortBy, sortDirection);
        GetAllResponse getAllResponse = productReviewService.getProductReviews(productId, rating, pageable);
        log.info("GetProductReviews - Product ID: {}, Rating: {} stars, Reviews Found: {}", productId, rating != null ? rating : "ALL", getAllResponse.getModels().size());
        return ResponseEntity.ok().body(getAllResponse);
    }
}
