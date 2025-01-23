package com.ecommerce.ecommerce_remake.feature.product_review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
public class RateRequest {

    private Integer rating;
    private String customerReview;

}
