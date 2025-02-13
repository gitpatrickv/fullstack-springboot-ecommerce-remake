package com.ecommerce.ecommerce_remake.feature.store_rating.service;

import com.ecommerce.ecommerce_remake.feature.order.model.Order;
import com.ecommerce.ecommerce_remake.feature.order.repository.OrderRepository;
import com.ecommerce.ecommerce_remake.feature.product_review.dto.RateRequest;
import com.ecommerce.ecommerce_remake.feature.store.repository.StoreRepository;
import com.ecommerce.ecommerce_remake.feature.store_rating.model.StoreRating;
import com.ecommerce.ecommerce_remake.feature.store_rating.repository.StoreRatingRepository;
import com.ecommerce.ecommerce_remake.web.exception.ReviewValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@Service
@Transactional
public class StoreRatingServiceImpl implements StoreRatingService {

    private final StoreRatingRepository storeRatingRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;

    @Override
    public void rateStore(RateRequest request, Integer storeId, Integer orderId, Integer userId) {

        this.validateStoreReview(userId, storeId);

        StoreRating storeRating = StoreRating.builder()
                .rating(request.getRating())
                .storeId(storeId)
                .userId(userId)
                .build();
        StoreRating savedReview = storeRatingRepository.saveAndFlush(storeRating);
        storeRepository.updateStoreAverageRating(savedReview.getStoreId());
        storeRepository.updateStoreReviewsCount(savedReview.getStoreId());

        this.updateOrderIfUserAlreadyRatedStore(userId, storeId);
    }

    @Override
    public Optional<StoreRating> findIfUserAlreadyRatedStore(Integer userId, Integer storeId) {
        return storeRatingRepository.findIfUserAlreadyRatedStore(userId, storeId);
    }

    private void validateStoreReview(int userId, int storeId){
        Optional<StoreRating> storeReview = this.findIfUserAlreadyRatedStore(userId, storeId);
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
