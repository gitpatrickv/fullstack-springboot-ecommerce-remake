package com.ecommerce.ecommerce_remake.feature.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountResponse {
    private Integer followerCount;
    private Integer productCount;
}
