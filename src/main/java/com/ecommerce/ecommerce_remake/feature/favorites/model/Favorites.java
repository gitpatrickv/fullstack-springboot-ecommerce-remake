package com.ecommerce.ecommerce_remake.feature.favorites.model;

import com.ecommerce.ecommerce_remake.common.dto.AuditEntity;
import com.ecommerce.ecommerce_remake.feature.product.model.Product;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "favorites")
public class Favorites extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer favoriteId;
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    public Favorites(Integer userId, Product product) {
        this.userId = userId;
        this.product = product;
    }
}
