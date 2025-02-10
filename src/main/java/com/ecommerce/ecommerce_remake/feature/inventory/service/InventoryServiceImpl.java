package com.ecommerce.ecommerce_remake.feature.inventory.service;

import com.ecommerce.ecommerce_remake.common.util.StrUtil;
import com.ecommerce.ecommerce_remake.common.util.mapper.ModelToEntityMapper;
import com.ecommerce.ecommerce_remake.feature.inventory.model.Inventory;
import com.ecommerce.ecommerce_remake.feature.inventory.model.InventoryModel;
import com.ecommerce.ecommerce_remake.feature.inventory.repository.InventoryRepository;
import com.ecommerce.ecommerce_remake.feature.product.model.Product;
import com.ecommerce.ecommerce_remake.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private ModelToEntityMapper<InventoryModel, Inventory> modelToEntityMapper = new ModelToEntityMapper<>(Inventory.class);

    private final InventoryRepository inventoryRepository;
    @Override
    public Set<Inventory> mapModelToInventory(Product product, Set<InventoryModel> inventoryModels) {

        Set<Inventory> inventories = new HashSet<>();

        for(InventoryModel inventoryModel : inventoryModels){
            Inventory inventory = modelToEntityMapper.map(inventoryModel);
            inventory.setProduct(product);
            inventories.add(inventory);
        }

        return inventories;
    }

    @Override
    public Optional<Inventory> findInventoryByProductId(String productId) {
        return inventoryRepository.findByProduct_ProductId(Integer.parseInt(productId));
    }

    @Override
    public Optional<Inventory> findInventoryByColorAndSize(String color, String size, String productId) {
        return inventoryRepository.findByColorIgnoreCaseAndSizeIgnoreCaseAndProduct_ProductId(color, size, Integer.parseInt(productId));
    }

    @Override
    public Inventory findInventoryById(Integer inventoryId) {
        return inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new ResourceNotFoundException(StrUtil.INVENTORY_NOT_FOUND));
    }


}
