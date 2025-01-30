package com.ecommerce.ecommerce_remake.feature.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreInfo {
    private String storeName;
    private Integer storeId;

}
