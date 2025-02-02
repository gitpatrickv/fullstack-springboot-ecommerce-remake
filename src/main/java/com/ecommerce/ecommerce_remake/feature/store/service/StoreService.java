package com.ecommerce.ecommerce_remake.feature.store.service;

import com.ecommerce.ecommerce_remake.common.dto.response.CountResponse;
import com.ecommerce.ecommerce_remake.feature.store.model.Store;
import com.ecommerce.ecommerce_remake.feature.store.model.StoreModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface StoreService {
    Optional<Store> getStoreById(String id);
    StoreModel getUserStore();
    void uploadStoreAvatar(MultipartFile file);
    CountResponse getStoreMetrics(Integer storeId);
}
