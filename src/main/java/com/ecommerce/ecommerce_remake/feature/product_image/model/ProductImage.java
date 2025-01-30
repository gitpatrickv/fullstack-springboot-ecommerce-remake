package com.ecommerce.ecommerce_remake.feature.product_image.model;

import com.ecommerce.ecommerce_remake.feature.product.model.Product;
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
@Table(name = "product_images")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imageId;
    private String productImage;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
