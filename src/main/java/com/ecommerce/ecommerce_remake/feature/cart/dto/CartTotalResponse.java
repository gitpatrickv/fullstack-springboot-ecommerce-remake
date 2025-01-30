package com.ecommerce.ecommerce_remake.feature.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartTotalResponse {
    private BigDecimal totalAmount;
    private Integer totalItems;
}
