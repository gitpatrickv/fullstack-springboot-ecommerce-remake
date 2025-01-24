package com.ecommerce.ecommerce_remake.feature.store.repository;

import com.ecommerce.ecommerce_remake.feature.store.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
    Optional<Store> findByStoreNameIgnoreCase(String storeName);
    Page<Store> findAll(Pageable pageable);
    @Modifying
    @Query(nativeQuery = true,
            value = """
                    UPDATE stores s
                    SET s.average_rating = COALESCE((SELECT AVG(sr.rating)
                    FROM store_reviews sr
                    WHERE sr.store_id = s.store_id), 0)
                    WHERE s.store_id = :storeId
                    """)
    void updateStoreAverageRating(@Param("storeId") Integer storeId);
    @Modifying
    @Query(nativeQuery = true,
            value = """
                    UPDATE stores s
                    SET s.reviews_count = (SELECT COUNT(sr.store_id)
                    FROM store_reviews sr
                    WHERE sr.store_id = s.store_id)
                    WHERE s.store_id = :storeId
                    """)
    void updateStoreReviewsCount(@Param("storeId") Integer storeId);
}
