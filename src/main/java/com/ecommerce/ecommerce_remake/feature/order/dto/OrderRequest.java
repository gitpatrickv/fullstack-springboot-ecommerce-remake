package com.ecommerce.ecommerce_remake.feature.order.dto;

import com.ecommerce.ecommerce_remake.feature.order.enums.PaymentMethod;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {

    private Set<Integer> ids;
    private PaymentMethod paymentMethod;
}
