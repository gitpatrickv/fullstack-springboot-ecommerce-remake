package com.ecommerce.ecommerce_remake.feature.product_review.service;

import com.ecommerce.ecommerce_remake.common.util.mapper.EntityToModelMapper;
import com.ecommerce.ecommerce_remake.common.util.mapper.ModelToEntityMapper;
import com.ecommerce.ecommerce_remake.feature.product.repository.ProductRepository;
import com.ecommerce.ecommerce_remake.feature.product_review.dto.RateRequest;
import com.ecommerce.ecommerce_remake.feature.product_review.model.ProductReview;
import com.ecommerce.ecommerce_remake.feature.product_review.model.ProductReviewModel;
import com.ecommerce.ecommerce_remake.feature.product_review.repository.ProductReviewRepository;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService {

    private ModelToEntityMapper<ProductReviewModel, ProductReview> modelToEntityMapper = new ModelToEntityMapper<>(ProductReview.class);
    private EntityToModelMapper<ProductReview, ProductReviewModel> entityToModelMapper = new EntityToModelMapper<>(ProductReviewModel.class);

    private final UserService userService;
    private final ProductReviewRepository productReviewRepository;
    private final ProductRepository productRepository;

    @Transactional

    @Override
    public void rateProduct(RateRequest request) {
        User user = userService.getCurrentAuthenticatedUser();
        ProductReview productReview = ProductReview.builder()
                .productId(request.getProductId())
                .rating(request.getRating())
                .customerReview(request.getCustomerReview())
                .user(user)
                .build();
        ProductReview savedReview = productReviewRepository.saveAndFlush(productReview);
        productRepository.updateProductAverageRating(savedReview.getProductId());
        productRepository.updateProductReviewsCount(savedReview.getProductId());
    }
}
