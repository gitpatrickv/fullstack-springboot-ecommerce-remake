package com.ecommerce.ecommerce_remake.feature.cart.repository;

import com.ecommerce.ecommerce_remake.feature.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    @Query("SELECT ci FROM CartItem ci WHERE ci.inventory.inventoryId = :inventoryId AND ci.cart.cartId = :cartId")
    Optional<CartItem> findItemIfExist(@Param("inventoryId") Integer inventoryId, @Param("cartId") Integer cartId);
}
