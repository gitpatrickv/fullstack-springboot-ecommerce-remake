package com.ecommerce.ecommerce_remake.feature.inventory.model;

import com.ecommerce.ecommerce_remake.common.dto.AuditEntity;
import com.ecommerce.ecommerce_remake.feature.cart.model.CartItem;
import com.ecommerce.ecommerce_remake.feature.product.model.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    private BigDecimal price;
    private Integer discountPercent;
    private String color;
    private String size;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();
}
