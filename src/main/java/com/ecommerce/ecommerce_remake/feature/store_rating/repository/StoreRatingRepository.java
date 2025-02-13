package com.ecommerce.ecommerce_remake.feature.store_rating.repository;

import com.ecommerce.ecommerce_remake.feature.store_rating.model.StoreRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRatingRepository extends JpaRepository<StoreRating, Integer> {

    @Query("SELECT sr FROM StoreRating sr WHERE sr.userId = :userId AND sr.storeId = :storeId")
    Optional<StoreRating> findIfUserAlreadyRatedStore(@Param("userId") Integer userId,
                                                      @Param("storeId") Integer storeId);
}
