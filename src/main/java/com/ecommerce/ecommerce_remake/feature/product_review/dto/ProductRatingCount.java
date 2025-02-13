package com.ecommerce.ecommerce_remake.feature.product_review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRatingCount {

    private int rating;
    private long count;
}
