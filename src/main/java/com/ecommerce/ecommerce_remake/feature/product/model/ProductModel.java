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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "{product.name.required}")
    private String productName;
    @NotNull(message = "{product.description.required}")
    private String description;
    private Integer totalSold;
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;
    @NotNull(message = "{product.category.required}")
    @Enumerated(EnumType.STRING)
    private Category category;
    @NotNull
    @Size(min = 1, message = "{inventory.list}")
    private Set<InventoryModel> inventories = new HashSet<>();

    private List<ProductImageModel> productImages = new ArrayList<>();

    private StoreModel store;
}
