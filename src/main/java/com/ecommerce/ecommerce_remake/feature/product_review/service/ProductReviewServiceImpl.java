package com.ecommerce.ecommerce_remake.feature.product_review.service;

import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.common.dto.response.PageResponse;
import com.ecommerce.ecommerce_remake.common.util.Pagination;
import com.ecommerce.ecommerce_remake.common.util.mapper.EntityToModelMapper;
import com.ecommerce.ecommerce_remake.feature.order.enums.OrderStatus;
import com.ecommerce.ecommerce_remake.feature.order.enums.ReviewStatus;
import com.ecommerce.ecommerce_remake.feature.order.model.Order;
import com.ecommerce.ecommerce_remake.feature.order.model.OrderItem;
import com.ecommerce.ecommerce_remake.feature.order.repository.OrderItemRepository;
import com.ecommerce.ecommerce_remake.feature.order.repository.OrderRepository;
import com.ecommerce.ecommerce_remake.feature.product.model.Product;
import com.ecommerce.ecommerce_remake.feature.product.repository.ProductRepository;
import com.ecommerce.ecommerce_remake.feature.product.service.ProductService;
import com.ecommerce.ecommerce_remake.feature.product_review.dto.ProductRatingCount;
import com.ecommerce.ecommerce_remake.feature.product_review.dto.RateRequest;
import com.ecommerce.ecommerce_remake.feature.product_review.dto.RatingCount;
import com.ecommerce.ecommerce_remake.feature.product_review.dto.ReplyRequest;
import com.ecommerce.ecommerce_remake.feature.product_review.model.ProductReview;
import com.ecommerce.ecommerce_remake.feature.product_review.model.ProductReviewModel;
import com.ecommerce.ecommerce_remake.feature.product_review.repository.ProductReviewRepository;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import com.ecommerce.ecommerce_remake.web.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce_remake.web.exception.ReviewValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final Pagination pagination;
    private final ProductService productService;

    private EntityToModelMapper<ProductReview, ProductReviewModel> entityToModelMapper = new EntityToModelMapper<>(ProductReviewModel.class);

    @Override
    public void rateProduct(RateRequest request, Integer productId, Integer orderId, Integer storeId) {
        User user = userService.getCurrentAuthenticatedUser();
        this.validateProductReview(user.getUserId(), productId);

        Product product = productService.getProductById(productId);

        ProductReview productReview = ProductReview.builder()
                .storeId(storeId)
                .rating(request.getRating())
                .customerReview(request.getCustomerReview())
                .user(user)
                .product(product)
                .build();
        productReviewRepository.saveAndFlush(productReview);
        this.updateOrderItemStatus(productId, orderId);
        productRepository.updateProductAverageRating(productId);
        productRepository.updateProductReviewsCount(productId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        this.updateOrderStatus(order);
    }

    @Override
    public RatingCount getProductRatingStarCount(String productId) {
        Integer id = Integer.parseInt(productId);
        List<ProductRatingCount> productRating = productReviewRepository.getRatingCountByProductId(id);
        if(productRating == null){
            return new RatingCount(0,0,0,0,0);
        }
        return this.getProductRatingMap(productRating);
    }

    @Override
    public GetAllResponse getProductReviews(String productId, Integer rating, Pageable pageable) {
        Integer id = Integer.parseInt(productId);
        Page<ProductReview> productReviews = productReviewRepository.getProductReviews(id, rating, pageable);
        return this.getReviews(productReviews, false);
    }

    @Override
    public GetAllResponse getProductReviewsByStore(Integer storeId, Pageable pageable) {
        Page<ProductReview> productReviews = productReviewRepository.findAllByStoreId(storeId, pageable);
        return this.getReviews(productReviews, true);
    }

    @Override
    public void replyToReview(ReplyRequest request) {
        ProductReview productReview = this.getProductReviewById(request.getProductReviewId());
        productReview.setSellerResponse(request.getReply());
        productReviewRepository.save(productReview);
    }

    private GetAllResponse getReviews(Page<ProductReview> productReviews, boolean isProductInfoNeeded) {
        PageResponse pageResponse = pagination.getPagination(productReviews);
        List<ProductReviewModel> productReviewModels = this.getProductReviewModelList(productReviews, isProductInfoNeeded);
        return new GetAllResponse(productReviewModels, pageResponse);
    }

    private List<ProductReviewModel> getProductReviewModelList(Page<ProductReview> productReviews, boolean isProductInfoNeeded) {
        return productReviews.stream()
                .map(productReview -> {
                    Product product = productReview.getProduct();
                    ProductReviewModel productReviewModel = entityToModelMapper.map(productReview);
                    productReviewModel.setName(productReview.getUser().getName());
                    productReviewModel.setUserImageUrl(productReview.getUser().getPicture());
                    if(isProductInfoNeeded) {
                        productReviewModel.setProductName(product.getProductName());
                        productReviewModel.setProductImageUrl(product.getProductImages().get(0).getProductImage());
                    }
                    return productReviewModel;
                })
                .toList();
    }

    private RatingCount getProductRatingMap(List<ProductRatingCount> productRatingList) {
        RatingCount ratingCount = new RatingCount(0,0,0,0,0);

        productRatingList.forEach(productRatingCount -> {
            int productRating = productRatingCount.getRating();
            long count = productRatingCount.getCount();

            switch (productRating) {
                case 1 -> ratingCount.setStar1(count);
                case 2 -> ratingCount.setStar2(count);
                case 3 -> ratingCount.setStar3(count);
                case 4 -> ratingCount.setStar4(count);
                case 5 -> ratingCount.setStar5(count);
            }
        });
        return ratingCount;
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
        boolean isAllReviewed = order.getOrderItems()
                .stream()
                .allMatch(orderItem -> orderItem.getReviewStatus().equals(ReviewStatus.REVIEWED));

        if(isAllReviewed){
            order.setOrderStatus(OrderStatus.RATED);
            orderRepository.save(order);
        }
    }

    private ProductReview getProductReviewById(Integer productReviewId) {
        return productReviewRepository.findById(productReviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Product review not found."));
    }
}
