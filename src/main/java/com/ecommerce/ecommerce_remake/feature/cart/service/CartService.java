package com.ecommerce.ecommerce_remake.feature.cart.service;

import com.ecommerce.ecommerce_remake.common.dto.response.Response;
import com.ecommerce.ecommerce_remake.feature.cart.dto.AddToCartRequest;
import com.ecommerce.ecommerce_remake.feature.cart.dto.CartTotalResponse;

import java.util.Set;

public interface CartService {

    Response addToCart(AddToCartRequest request);
    Response addToCartWithVariation(AddToCartRequest request, String color, String size);
    Integer getCartSize();
    CartTotalResponse getCartTotal(Set<Integer> ids);
}
