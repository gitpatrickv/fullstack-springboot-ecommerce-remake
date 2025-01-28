package com.ecommerce.ecommerce_remake.feature.product.dto;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.feature.product.enums.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoResponse extends Model {

    private Integer productId;
    private String productName;
    private String slug;
    private String description;
    private Integer totalSold;
    private BigDecimal averageRating;
    private Integer reviewsCount;
    private LocalDateTime createdDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Category category;
    private BigDecimal price;
    private String productImage;

}
