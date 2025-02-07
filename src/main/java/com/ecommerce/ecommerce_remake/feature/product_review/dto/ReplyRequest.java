package com.ecommerce.ecommerce_remake.feature.product_review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
public class ReplyRequest {
    @NotNull
    private Integer productReviewId;

    @NotBlank(message = "Your reply must contain at least one character.")
    private String reply;

}
