package com.ecommerce.ecommerce_remake.feature.store.repository;

import com.ecommerce.ecommerce_remake.feature.store.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByStoreNameIgnoreCase(String storeName);
    Optional<Store> findByContactNumber(String number);
}
