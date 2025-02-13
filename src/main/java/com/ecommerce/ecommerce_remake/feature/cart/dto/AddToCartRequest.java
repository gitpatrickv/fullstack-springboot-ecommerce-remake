package com.ecommerce.ecommerce_remake.feature.cart.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddToCartRequest {
    @NotNull
    private String productId;
    @NotNull
    private Integer quantity;

}
