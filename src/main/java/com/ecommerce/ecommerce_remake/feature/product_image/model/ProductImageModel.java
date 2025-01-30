package com.ecommerce.ecommerce_remake.feature.product_image.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageModel {
    private Integer imageId;
    private String productImage;
}
