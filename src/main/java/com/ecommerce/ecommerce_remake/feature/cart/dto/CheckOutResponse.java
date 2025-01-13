package com.ecommerce.ecommerce_remake.feature.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckOutResponse {
    private BigDecimal totalAmount;
    private List<CartItemsResponse> cartItemsResponse;
}

