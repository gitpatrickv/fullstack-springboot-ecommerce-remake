package com.ecommerce.ecommerce_remake.feature.cart.model;

import com.ecommerce.ecommerce_remake.common.dto.AuditEntity;
import com.ecommerce.ecommerce_remake.feature.inventory.model.Inventory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartItemId;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

}
