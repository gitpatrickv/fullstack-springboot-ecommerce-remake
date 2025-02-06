package com.ecommerce.ecommerce_remake.feature.store_following.service;

import com.ecommerce.ecommerce_remake.feature.store_following.dto.Following;
import com.ecommerce.ecommerce_remake.feature.store_following.dto.StoreFollowListResponse;

import java.util.List;

public interface StoreFollowingService {

    void followStore(Integer storeId, Integer userId);
    Following getFollowingStoreStatus(Integer userId, Integer storeId);
    List<StoreFollowListResponse> getAllFollowedStores(Integer userId);
}
