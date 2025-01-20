package com.ecommerce.ecommerce_remake.feature.store_review.repository;

import com.ecommerce.ecommerce_remake.feature.store_review.model.StoreReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreReviewRepository extends JpaRepository<StoreReview, Integer> {
}
