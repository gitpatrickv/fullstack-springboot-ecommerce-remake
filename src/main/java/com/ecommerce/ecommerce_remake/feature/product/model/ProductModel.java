package com.ecommerce.ecommerce_remake.feature.product.model;

import com.ecommerce.ecommerce_remake.common.dto.Model;
import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.common.marker.CreateInfo;
import com.ecommerce.ecommerce_remake.common.marker.UpdateInfo;
import com.ecommerce.ecommerce_remake.feature.inventory.model.InventoryModel;
import com.ecommerce.ecommerce_remake.feature.product.enums.Category;
import com.ecommerce.ecommerce_remake.feature.product_image.model.ProductImageModel;
import com.ecommerce.ecommerce_remake.feature.store.model.StoreModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @Null(groups = CreateInfo.class)
    @NotNull(groups = UpdateInfo.class)
    private Integer productId;
    @NotNull(message = "{product.name.required}")
    private String productName;
    private String slug;
    @NotNull(message = "{product.description.required}")
    private String description;
    private Integer totalSold;
    private BigDecimal averageRating;
    private Integer reviewsCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Category category;
    @NotNull(groups = CreateInfo.class)
    @Size(min = 1, message = "{inventory.list}",groups = CreateInfo.class)
    private Set<InventoryModel> inventories = new HashSet<>();

    private List<ProductImageModel> productImages = new ArrayList<>();

    private StoreModel store;

}
