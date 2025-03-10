package com.ecommerce.ecommerce_remake.feature.store_following.repository;

import com.ecommerce.ecommerce_remake.feature.store_following.model.StoreFollowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreFollowingRepository extends JpaRepository<StoreFollowing, Integer> {

    Optional<StoreFollowing> findByUserIdAndStore_StoreId(Integer userId, Integer storeId);
    void deleteByUserIdAndStore_StoreId(Integer userId, Integer storeId);
    List<StoreFollowing> findAllByUserId(Integer userId);
    @Query("SELECT COUNT(sf) FROM StoreFollowing sf WHERE sf.store.storeId = :storeId")
    Integer countStoreFollowers(@Param("storeId") Integer storeId);
}
