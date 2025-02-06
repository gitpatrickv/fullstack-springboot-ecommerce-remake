package com.ecommerce.ecommerce_remake.feature.store_review.service;

import com.ecommerce.ecommerce_remake.feature.order.model.Order;
import com.ecommerce.ecommerce_remake.feature.order.repository.OrderRepository;
import com.ecommerce.ecommerce_remake.feature.product_review.dto.RateRequest;
import com.ecommerce.ecommerce_remake.feature.store.repository.StoreRepository;
import com.ecommerce.ecommerce_remake.feature.store_review.model.StoreReview;
import com.ecommerce.ecommerce_remake.feature.store_review.repository.StoreReviewRepository;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import com.ecommerce.ecommerce_remake.web.exception.ReviewValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
@Transactional
public class StoreReviewServiceImpl implements StoreReviewService{

    private final UserService userService;
    private final StoreReviewRepository storeReviewRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;

    @Override
    public void rateStore(RateRequest request, Integer storeId, Integer orderId) {
        User user = userService.getCurrentAuthenticatedUser();

        this.validateStoreReview(user.getUserId(), storeId);

        StoreReview storeReview = StoreReview.builder()
                .rating(request.getRating())
                .storeId(storeId)
                .user(user)
                .build();
        StoreReview savedReview = storeReviewRepository.saveAndFlush(storeReview);
        storeRepository.updateStoreAverageRating(savedReview.getStoreId());
        storeRepository.updateStoreReviewsCount(savedReview.getStoreId());

        this.updateOrderIfUserAlreadyRatedStore(user.getUserId(), storeId);
    }

    @Override
    public Optional<StoreReview> findIfUserAlreadyRatedStore(Integer userId, Integer storeId) {
        return storeReviewRepository.findIfUserAlreadyRatedStore(userId, storeId);
    }

    private void validateStoreReview(int userId, int storeId){
        Optional<StoreReview> storeReview = this.findIfUserAlreadyRatedStore(userId, storeId);
        if(storeReview.isPresent()){
            throw new ReviewValidationException("You have already submitted a review for this store.");
        }
    }
    //marks all orders from the specified store as rated, including past orders, if the user has rated the store.
    public void updateOrderIfUserAlreadyRatedStore(int userId, int storeId){
        List<Order> orders = orderRepository.findAllByUserIdAndStore_StoreId(userId, storeId);
        orders.forEach(order -> {
            order.setIsStoreRated(true);
            orderRepository.save(order);
        });
    }

}
