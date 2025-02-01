package com.ecommerce.ecommerce_remake.feature.store_following.repository;

import com.ecommerce.ecommerce_remake.feature.store_following.model.StoreFollowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreFollowingRepository extends JpaRepository<StoreFollowing, Integer> {
}
