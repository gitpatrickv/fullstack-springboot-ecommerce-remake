package com.ecommerce.ecommerce_remake.feature.store_following.service;

import com.ecommerce.ecommerce_remake.feature.store_following.dto.Following;

public interface StoreFollowingService {

    void followStore(Integer storeId);

    Following getFollowingStoreStatus(Integer userId, Integer storeId);
}
