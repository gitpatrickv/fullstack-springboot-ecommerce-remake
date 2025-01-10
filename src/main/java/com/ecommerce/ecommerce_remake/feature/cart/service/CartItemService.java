package com.ecommerce.ecommerce_remake.feature.cart.service;

import com.ecommerce.ecommerce_remake.feature.cart.dto.CartItemsResponse;
import com.ecommerce.ecommerce_remake.feature.cart.model.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemService {

    Optional<CartItem> findExistingCartItem(Integer inventoryId, Integer cartId);
    List<CartItemsResponse> getAllCartItems();
    void updateQuantity(Integer cartItemId, Integer newQuantity);
    CartItem findCartItemById(Integer id);


}
