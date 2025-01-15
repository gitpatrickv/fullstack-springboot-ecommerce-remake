package com.ecommerce.ecommerce_remake.feature.order.model;

import com.ecommerce.ecommerce_remake.feature.inventory.model.InventoryModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemModel {

    private Integer orderItemId;
    private Integer productQuantity;
    private InventoryModel inventory;
}
