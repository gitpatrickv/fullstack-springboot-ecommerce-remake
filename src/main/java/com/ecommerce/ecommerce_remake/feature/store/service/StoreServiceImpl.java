package com.ecommerce.ecommerce_remake.feature.store.service;

import com.ecommerce.ecommerce_remake.common.dto.response.CountResponse;
import com.ecommerce.ecommerce_remake.common.util.mapper.EntityToModelMapper;
import com.ecommerce.ecommerce_remake.feature.product.repository.ProductRepository;
import com.ecommerce.ecommerce_remake.feature.product_image.service.ProductImageService;
import com.ecommerce.ecommerce_remake.feature.store.model.Store;
import com.ecommerce.ecommerce_remake.feature.store.model.StoreModel;
import com.ecommerce.ecommerce_remake.feature.store.repository.StoreRepository;
import com.ecommerce.ecommerce_remake.feature.store_following.repository.StoreFollowingRepository;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import com.ecommerce.ecommerce_remake.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;
    private final ProductImageService productImageService;
    private final ProductRepository productRepository;
    private final StoreFollowingRepository storeFollowingRepository;

    private EntityToModelMapper<Store, StoreModel> entityToModelMapper = new EntityToModelMapper<>(StoreModel.class);

    @Override
    public Optional<Store> getStoreById(String id) {
        return storeRepository.findById(Integer.parseInt(id));
    }

    @Override
    public StoreModel getUserStore() {
        return Optional.ofNullable(userService.getCurrentAuthenticatedUser())
                .map(User::getStore)
                .map(entityToModelMapper::map)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found."));
    }

    @Override
    public void uploadStoreAvatar(MultipartFile file) {
        User user = userService.getCurrentAuthenticatedUser();
        Store store = user.getStore();
        store.setPicture(productImageService.processImages(file));
        storeRepository.save(store);
    }


    @Override
    public CountResponse getStoreMetrics(Integer storeId) {
        Integer followerCount = storeFollowingRepository.countStoreFollowers(storeId);
        Integer productCount = productRepository.getStoreProductCount(storeId);
        return new CountResponse(followerCount, productCount);
    }


}
