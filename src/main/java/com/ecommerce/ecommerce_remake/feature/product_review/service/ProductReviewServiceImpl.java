package com.ecommerce.ecommerce_remake.feature.product_review.service;

import com.ecommerce.ecommerce_remake.feature.product.repository.ProductRepository;
import com.ecommerce.ecommerce_remake.feature.product_review.dto.RateRequest;
import com.ecommerce.ecommerce_remake.feature.product_review.model.ProductReview;
import com.ecommerce.ecommerce_remake.feature.product_review.repository.ProductReviewRepository;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import com.ecommerce.ecommerce_remake.web.exception.ReviewValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService {



    private final UserService userService;
    private final ProductReviewRepository productReviewRepository;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public void rateProduct(RateRequest request, Integer productId) {
        User user = userService.getCurrentAuthenticatedUser();

        this.validateProductReview(user.getUserId(), productId);

        ProductReview productReview = ProductReview.builder()
                .productId(productId)
                .rating(request.getRating())
                .customerReview(request.getCustomerReview())
                .user(user)
                .build();
        ProductReview savedReview = productReviewRepository.saveAndFlush(productReview);
        productRepository.updateProductAverageRating(savedReview.getProductId());
        productRepository.updateProductReviewsCount(savedReview.getProductId());
    }

    @Override
    public Optional<ProductReview> findReviewByUserAndProduct(Integer userId, Integer productId) {
        return productReviewRepository.findIfReviewAlreadyExistForUser(userId, productId);
    }

    private void validateProductReview(Integer userId, Integer productId){
        Optional<ProductReview> productReview = this.findReviewByUserAndProduct(userId, productId);
        if(productReview.isPresent()){
            throw new ReviewValidationException();
        }
    }
}
