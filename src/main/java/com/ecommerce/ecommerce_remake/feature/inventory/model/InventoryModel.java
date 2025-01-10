package com.ecommerce.ecommerce_remake.feature.inventory.model;

import com.ecommerce.ecommerce_remake.common.dto.Model;
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
