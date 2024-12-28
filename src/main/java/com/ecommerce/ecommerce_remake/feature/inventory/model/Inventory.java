package com.ecommerce.ecommerce_remake.feature.inventory.model;

import com.ecommerce.ecommerce_remake.common.dto.AuditEntity;
import com.ecommerce.ecommerce_remake.feature.product.model.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventory")
@EqualsAndHashCode(of = {"product", "color", "size"}, callSuper = false)
public class Inventory extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inventoryId;
    private Integer quantity;
    private Integer price;
    private String color;
    private String size;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
