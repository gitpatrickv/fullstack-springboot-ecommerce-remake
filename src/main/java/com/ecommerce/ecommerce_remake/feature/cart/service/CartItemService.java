package com.ecommerce.ecommerce_remake.feature.cart.service;

import com.ecommerce.ecommerce_remake.feature.cart.model.CartItem;

import java.util.Optional;

public interface CartItemService {

    Optional<CartItem> findExistingCartItem(Integer inventoryId, Integer cartId);
}
