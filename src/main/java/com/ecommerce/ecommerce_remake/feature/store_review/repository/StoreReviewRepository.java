package com.ecommerce.ecommerce_remake.feature.store_review.repository;

import com.ecommerce.ecommerce_remake.feature.store_review.model.StoreReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreReviewRepository extends JpaRepository<StoreReview, Integer> {

    @Query("SELECT sr FROM StoreReview sr WHERE sr.user.userId = :userId AND sr.storeId = :storeId")
    Optional<StoreReview> findIfUserAlreadyRatedStore(@Param("userId") Integer userId,
                                                      @Param("storeId") Integer storeId);
}
