package com.ecommerce.ecommerce_remake.feature.cart.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DeleteRequest {

    private Set<Integer> ids;
}
