package com.ecommerce.ecommerce_remake.feature.inventory.service;

import com.ecommerce.ecommerce_remake.feature.inventory.model.Inventory;
import com.ecommerce.ecommerce_remake.feature.inventory.model.InventoryModel;
import com.ecommerce.ecommerce_remake.feature.product.model.Product;

import java.util.Set;

public interface InventoryService {

    Set<Inventory> mapModelToInventory(Product product, Set<InventoryModel> inventoryModels);
}
