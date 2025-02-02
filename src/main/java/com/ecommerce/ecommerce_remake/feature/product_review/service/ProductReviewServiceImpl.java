package com.ecommerce.ecommerce_remake.feature.product_review.service;

import com.ecommerce.ecommerce_remake.feature.order.enums.OrderStatus;
import com.ecommerce.ecommerce_remake.feature.order.enums.ReviewStatus;
import com.ecommerce.ecommerce_remake.feature.order.model.Order;
import com.ecommerce.ecommerce_remake.feature.order.model.OrderItem;
import com.ecommerce.ecommerce_remake.feature.order.repository.OrderItemRepository;
import com.ecommerce.ecommerce_remake.feature.order.repository.OrderRepository;
import com.ecommerce.ecommerce_remake.feature.product.repository.ProductRepository;
import com.ecommerce.ecommerce_remake.feature.product_review.dto.ProductRatingCount;
import com.ecommerce.ecommerce_remake.feature.product_review.dto.RateRequest;
import com.ecommerce.ecommerce_remake.feature.product_review.dto.RatingCount;
import com.ecommerce.ecommerce_remake.feature.product_review.model.ProductReview;
import com.ecommerce.ecommerce_remake.feature.product_review.repository.ProductReviewRepository;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import com.ecommerce.ecommerce_remake.web.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce_remake.web.exception.ReviewValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductReviewServiceImpl implements ProductReviewService {

    private final UserService userService;
    private final ProductReviewRepository productReviewRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;


    @Override
    public void rateProduct(RateRequest request, Integer productId, Integer orderId) {
        User user = userService.getCurrentAuthenticatedUser();

        this.validateProductReview(user.getUserId(), productId);

        ProductReview productReview = ProductReview.builder()
                .productId(productId)
                .rating(request.getRating())
                .customerReview(request.getCustomerReview())
                .user(user)
                .build();
        ProductReview savedReview = productReviewRepository.saveAndFlush(productReview);
        this.updateOrderItemStatus(productId, orderId);
        productRepository.updateProductAverageRating(savedReview.getProductId());
        productRepository.updateProductReviewsCount(savedReview.getProductId());

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        this.updateOrderStatus(order);
    }

    @Override
    public Optional<ProductReview> findReviewByUserAndProduct(Integer userId, Integer productId) {
        return productReviewRepository.findIfReviewAlreadyExistForUser(userId, productId);
    }

    private void validateProductReview(Integer userId, Integer productId){
        Optional<ProductReview> productReview = this.findReviewByUserAndProduct(userId, productId);
        if(productReview.isPresent()){
            throw new ReviewValidationException("You have already submitted a review for this product.");
        }
    }
    //it's a list because variations have the same productId
    private void updateOrderItemStatus(Integer productId, Integer orderId){
        List<OrderItem> orderItems = orderItemRepository.findAllByProductIdAndOrder_OrderId(productId, orderId);
        orderItems.forEach(orderItem -> {
            orderItem.setReviewStatus(ReviewStatus.REVIEWED);
            orderItemRepository.saveAndFlush(orderItem);
        });
    }
    //if all order items are already reviewed, the order status will be changed to RATED
    @Override
    public void updateOrderStatus(Order order){
        boolean isAllReviewed = order
                .getOrderItems()
                .stream()
                .allMatch(orderItem -> orderItem.getReviewStatus().equals(ReviewStatus.REVIEWED));

        if(isAllReviewed){
            order.setOrderStatus(OrderStatus.RATED);
            orderRepository.save(order);
        }
    }

    @Override
    public RatingCount getProductRatingStarCount(Integer productId) {
        List<ProductRatingCount> productRating = productReviewRepository.getRatingCountByProductId(productId);
        if(productRating == null){
            return new RatingCount(0,0,0,0,0);
        }
        return this.getProductRatingMap(productRating);
    }

    private RatingCount getProductRatingMap(List<ProductRatingCount> productRatingList) {
        RatingCount ratingCount = new RatingCount(0,0,0,0,0);

        productRatingList.forEach(productRatingCount -> {
            int productRating = productRatingCount.getRating();
            long count = productRatingCount.getCount();

            switch (productRating) {
                case 1:
                    ratingCount.setStar1((int) count);
                    break;
                case 2:
                    ratingCount.setStar2((int) count);
                    break;
                case 3:
                    ratingCount.setStar3((int) count);
                    break;
                case 4:
                    ratingCount.setStar4((int) count);
                    break;
                case 5:
                    ratingCount.setStar5((int) count);
                    break;
            }
        });
        return ratingCount;
    }
}
