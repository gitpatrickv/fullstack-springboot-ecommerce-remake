package com.ecommerce.ecommerce_remake.feature.store.service;

import com.ecommerce.ecommerce_remake.feature.store.model.Store;

import java.util.Optional;

public interface StoreService {
    Optional<Store> getStoreById(String id);
}
