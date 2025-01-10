package com.ecommerce.ecommerce_remake.feature.cart.service;

import com.ecommerce.ecommerce_remake.common.dto.response.Response;
import com.ecommerce.ecommerce_remake.feature.cart.dto.AddToCartRequest;

public interface CartService {

    Response addToCart(AddToCartRequest request);
    Response addToCartWithVariation(AddToCartRequest request, String color, String size);
    Integer getCartSize();
}
