package com.ecommerce.ecommerce_remake.feature.inventory.service;

import com.ecommerce.ecommerce_remake.common.util.mapper.EntityToModelMapper;
import com.ecommerce.ecommerce_remake.common.util.mapper.ModelToEntityMapper;
import com.ecommerce.ecommerce_remake.feature.inventory.model.Inventory;
import com.ecommerce.ecommerce_remake.feature.inventory.model.InventoryModel;
import com.ecommerce.ecommerce_remake.feature.product.model.Product;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Service
public class InventoryServiceImpl implements InventoryService {

    private ModelToEntityMapper<InventoryModel, Inventory> modelToEntityMapper = new ModelToEntityMapper<>(Inventory.class);
    private EntityToModelMapper<Inventory, InventoryModel> entityToModelMapper = new EntityToModelMapper<>(InventoryModel.class);
    @Override
    public Set<Inventory> inventories(Product product, Set<InventoryModel> inventoryModels) {

        Set<Inventory> inventories = new HashSet<>();

        for(InventoryModel inventoryModel : inventoryModels){
            Inventory inventory = modelToEntityMapper.map(inventoryModel);
            inventory.setProduct(product);
            inventories.add(inventory);
        }

        return inventories;
    }
}
