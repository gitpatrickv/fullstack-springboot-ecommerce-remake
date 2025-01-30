package com.ecommerce.ecommerce_remake.feature.store.model;

import com.ecommerce.ecommerce_remake.common.dto.AuditEntity;
import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.feature.order.model.Order;
import com.ecommerce.ecommerce_remake.feature.product.model.Product;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stores")
public class Store extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storeId;
    private String storeName;
    private String contactNumber;
    private String picture;
    private BigDecimal averageRating;
    private Integer reviewsCount;
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Product> product = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<Order> orders = new ArrayList<>();

}
