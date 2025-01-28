package com.ecommerce.ecommerce_remake.feature.product.dto;

import com.ecommerce.ecommerce_remake.feature.product.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreCategory {
    private Category category;
}
