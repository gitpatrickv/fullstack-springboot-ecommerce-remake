package com.ecommerce.ecommerce_remake.feature.cart.dto;

import com.ecommerce.ecommerce_remake.feature.cart.model.CartItemModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemsResponse {
    private String storeName;
    private List<CartItemModel> cartItems;
}
