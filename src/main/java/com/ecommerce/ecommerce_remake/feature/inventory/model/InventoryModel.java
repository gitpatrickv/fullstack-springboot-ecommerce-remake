package com.ecommerce.ecommerce_remake.feature.inventory.model;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryModel extends Model {
    private Integer inventoryId;
    private Integer quantity;
    private Integer price;
    private String color;
    private String size;

}
