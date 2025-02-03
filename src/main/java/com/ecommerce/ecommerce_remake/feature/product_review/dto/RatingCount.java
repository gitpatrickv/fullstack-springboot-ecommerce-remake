package com.ecommerce.ecommerce_remake.feature.product_review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingCount {

    private long star1;
    private long star2;
    private long star3;
    private long star4;
    private long star5;

}
