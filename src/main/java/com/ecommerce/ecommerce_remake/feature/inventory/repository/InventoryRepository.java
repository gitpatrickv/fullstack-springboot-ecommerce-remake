package com.ecommerce.ecommerce_remake.feature.inventory.repository;

import com.ecommerce.ecommerce_remake.feature.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
}
