package com.ecommerce.ecommerce_remake.feature.inventory.repository;

import com.ecommerce.ecommerce_remake.feature.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    Optional<Inventory> findByProduct_ProductId(Integer productId);
    Optional<Inventory> findByColorIgnoreCaseAndSizeIgnoreCaseAndProduct_ProductId(String color, String size, Integer productId);
}
