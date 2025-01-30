package com.ecommerce.ecommerce_remake.feature.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class IdSetRequest {

    private Set<Integer> ids;
}
