package com.ecommerce.ecommerce_remake.feature.inventory.model;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryModel extends Model {
    private Integer inventoryId;
    @NotNull(message = "{quantity.required}")
    private Integer quantity;
    @NotNull(message = "{price.required}")
    private Integer price;
    private Integer discountPercent;
    private Integer discountedPrice;
    private String color;
    private String size;
}
