package com.ecommerce.ecommerce_remake.feature.store.repository;

import com.ecommerce.ecommerce_remake.feature.store.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
    Optional<Store> findByStoreNameIgnoreCase(String storeName);
    Optional<Store> findByContactNumber(String number);
}
