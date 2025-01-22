package com.ecommerce.ecommerce_remake.feature.product_review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RateRequest {

    @NotNull
    private Integer productId;
    @NotNull(message = "{rating.required}")
    private Integer rating;
    private String customerReview;

}
