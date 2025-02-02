package com.ecommerce.ecommerce_remake.feature.product_review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingCount {

    private int star1;
    private int star2;
    private int star3;
    private int star4;
    private int star5;

}
