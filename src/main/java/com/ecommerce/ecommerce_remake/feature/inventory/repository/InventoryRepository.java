package com.ecommerce.ecommerce_remake.feature.inventory.repository;

import com.ecommerce.ecommerce_remake.feature.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    Optional<Inventory> findByProduct_ProductId(Integer productId);
    Optional<Inventory> findByColorIgnoreCaseAndSizeIgnoreCaseAndProduct_ProductId(String color, String size, Integer productId);

    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE inventory i " +
                    "SET quantity = CASE " +
                    "WHEN quantity >= :quantityToSubtract THEN quantity - :quantityToSubtract " +
                    "ELSE quantity END " +
                    "WHERE i.inventory_id = :inventoryId")
    void subtractInventoryStockOnOrder(@Param("inventoryId") Integer inventoryId, @Param("quantityToSubtract") int quantityToSubtract);

    @Modifying
    @Query(nativeQuery = true,
        value = "UPDATE inventory i " +
                "SET quantity = quantity + :quantityToAdd " +
                "WHERE i.inventory_id = :inventoryId")
    void addInventoryStockOnOrderCancellation(@Param("inventoryId") Integer inventoryId, @Param("quantityToAdd") int quantityToAdd);

}
