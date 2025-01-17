package com.ecommerce.ecommerce_remake.feature.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemModel {

    private Integer orderItemId;
    private Integer quantity;
    private String productName;
    private String productImage;
    private BigDecimal productPrice;
    private String color;
    private String size;
}
