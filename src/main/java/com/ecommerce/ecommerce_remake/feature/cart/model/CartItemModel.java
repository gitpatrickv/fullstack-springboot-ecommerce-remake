package com.ecommerce.ecommerce_remake.feature.cart.model;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import com.ecommerce.ecommerce_remake.feature.inventory.model.InventoryModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemModel extends Model {
    private Integer cartItemId;
    private Integer quantity;
    private InventoryModel inventory;
    private String productName;
    private String productImage;
}
