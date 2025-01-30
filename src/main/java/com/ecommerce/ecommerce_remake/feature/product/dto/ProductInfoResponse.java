package com.ecommerce.ecommerce_remake.feature.product.dto;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoResponse extends Model {

    private Integer productId;
    private String productName;
    private Integer totalSold;
    private BigDecimal averageRating;
    private BigDecimal price;
    private String productImage;

}
