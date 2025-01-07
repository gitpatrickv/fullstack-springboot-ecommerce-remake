package com.ecommerce.ecommerce_remake.feature.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddToCartRequest {
    private Integer productId;
    private Integer inventoryId;
    private Integer quantity;
}
