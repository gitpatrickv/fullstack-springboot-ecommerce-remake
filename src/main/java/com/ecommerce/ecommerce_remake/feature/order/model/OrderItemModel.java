package com.ecommerce.ecommerce_remake.feature.order.model;

import com.ecommerce.ecommerce_remake.feature.order.enums.ReviewStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_DEFAULT)
public class OrderItemModel {

    private Integer orderItemId;
    private Integer quantity;
    private String productName;
    private String productImage;
    private Integer productId;
    private BigDecimal productPrice;
    private String color;
    private String size;
    @Enumerated(EnumType.STRING)
    private ReviewStatus reviewStatus;
}
