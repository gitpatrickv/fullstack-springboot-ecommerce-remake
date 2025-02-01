package com.ecommerce.ecommerce_remake.feature.cart.service;

import com.ecommerce.ecommerce_remake.feature.cart.dto.AddToCartRequest;
import com.ecommerce.ecommerce_remake.feature.cart.dto.CartTotalResponse;
import com.ecommerce.ecommerce_remake.feature.cart.model.Cart;

import java.util.Set;

public interface CartService {

    void addToCart(AddToCartRequest request);
    void addToCartWithVariation(AddToCartRequest request, String color, String size);
    CartTotalResponse getCartTotal(Set<Integer> ids, Integer cartId);
    Cart getCartById(Integer cartId);

}
