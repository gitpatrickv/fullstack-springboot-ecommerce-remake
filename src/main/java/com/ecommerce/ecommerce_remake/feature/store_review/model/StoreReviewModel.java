package com.ecommerce.ecommerce_remake.feature.store_review.model;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import com.ecommerce.ecommerce_remake.feature.user.model.UserModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_DEFAULT)
public class StoreReviewModel extends Model {

    private Integer storeReviewId;
    @NotNull
    private Integer storeId;
    @NotNull(message = "{rating.required}")
    private Integer rating;
    private String customerReview;
    private UserModel user;
}
