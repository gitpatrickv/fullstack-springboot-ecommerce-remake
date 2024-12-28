package com.ecommerce.ecommerce_remake.feature.product.model;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import com.ecommerce.ecommerce_remake.feature.inventory.model.InventoryModel;
import com.ecommerce.ecommerce_remake.feature.product.enums.Category;
import com.ecommerce.ecommerce_remake.feature.product.enums.ProductStatus;
import com.ecommerce.ecommerce_remake.feature.product_image.model.ProductImageModel;
import com.ecommerce.ecommerce_remake.feature.store.model.StoreModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_DEFAULT)
public class ProductModel extends Model {
    private Integer productId;
    private String productName;
    private String description;
    private Integer totalSold;
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;
    @Enumerated(EnumType.STRING)
    private Category category;

    private Set<InventoryModel> inventories = new HashSet<>();

    private List<ProductImageModel> productImages = new ArrayList<>();

    private StoreModel store;
}
