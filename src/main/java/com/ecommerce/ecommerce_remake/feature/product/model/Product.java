
package com.ecommerce.ecommerce_remake.feature.product.model;

import com.ecommerce.ecommerce_remake.common.dto.AuditEntity;
import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.feature.inventory.model.Inventory;
import com.ecommerce.ecommerce_remake.feature.product.enums.Category;
import com.ecommerce.ecommerce_remake.feature.product_image.model.ProductImage;
import com.ecommerce.ecommerce_remake.feature.store.model.Store;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String productName;
    private String slug;
    private String description;
    private Integer totalSold;
    private BigDecimal averageRating;
    private Integer reviewsCount;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<Inventory> inventories = new HashSet<>();

    @OneToMany(mappedBy = "product",  cascade = CascadeType.ALL)
    private List<ProductImage> productImages = new ArrayList<>();

    @PrePersist
    @PreUpdate
    public void generateSlug() {
        if (productName != null && (slug == null || slug.isEmpty())) {
            this.slug = toSlug(productName);
        }
    }

    private String toSlug(String input) {
        return input.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .trim()
                .replaceAll("\\s+", "-");
    }
}

