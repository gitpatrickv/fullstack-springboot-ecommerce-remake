package com.ecommerce.ecommerce_remake.feature.product_review.model;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_DEFAULT)
public class ProductReviewModel extends Model {

    private Integer productReviewId;
    private Integer rating;
    private String customerReview;
    private String sellerResponse;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;
    private String name;
    private String userImageUrl;
    private String productName;
    private String productImageUrl;

}
