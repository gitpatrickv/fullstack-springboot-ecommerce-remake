package com.ecommerce.ecommerce_remake.feature.store_following.service;

import com.ecommerce.ecommerce_remake.feature.store.model.Store;
import com.ecommerce.ecommerce_remake.feature.store.service.StoreService;
import com.ecommerce.ecommerce_remake.feature.store_following.dto.Following;
import com.ecommerce.ecommerce_remake.feature.store_following.dto.StoreFollowListResponse;
import com.ecommerce.ecommerce_remake.feature.store_following.model.StoreFollowing;
import com.ecommerce.ecommerce_remake.feature.store_following.repository.StoreFollowingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreFollowingServiceImpl implements StoreFollowingService{

    private final StoreFollowingRepository storeFollowingRepository;
    private final StoreService storeService;
    @Override
    public void followStore(Integer storeId, Integer userId) {
        Optional<StoreFollowing> storeFollowing = this.getStoreFollowing(userId, storeId);

        if(storeFollowing.isPresent()){
            storeFollowingRepository.deleteByUserIdAndStore_StoreId(userId, storeId);
        } else {
            Optional<Store> store = storeService.getStoreById(String.valueOf(storeId));
            StoreFollowing following = new StoreFollowing(userId, store.get());
            storeFollowingRepository.save(following);
        }
    }

    @Override
    public Following getFollowingStoreStatus(Integer userId, Integer storeId) {
        Optional<StoreFollowing> storeFollowing = this.getStoreFollowing(userId, storeId);
        return new Following(storeFollowing.isPresent());
    }

    @Override
    public List<StoreFollowListResponse> getAllFollowedStores(Integer userId) {
        return storeFollowingRepository.findAllByUserId(userId).stream()
                .map(storeFollowing -> {
                    Store store = storeFollowing.getStore();
                    return new StoreFollowListResponse(store.getStoreId(), store.getStoreName(), store.getPicture());
                }).toList();
    }


    private Optional<StoreFollowing> getStoreFollowing(Integer userId, Integer storeId){
        return storeFollowingRepository.findByUserIdAndStore_StoreId(userId, storeId);
    }
}
