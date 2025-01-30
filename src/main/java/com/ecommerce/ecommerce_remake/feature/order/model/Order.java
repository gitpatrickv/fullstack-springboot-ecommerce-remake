package com.ecommerce.ecommerce_remake.feature.order.model;

import com.ecommerce.ecommerce_remake.common.dto.AuditEntity;
import com.ecommerce.ecommerce_remake.feature.order.enums.OrderStatus;
import com.ecommerce.ecommerce_remake.feature.order.enums.PaymentMethod;
import com.ecommerce.ecommerce_remake.feature.store.model.Store;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
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
@Builder
@Table(name = "orders")
public class Order extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    private Integer itemQuantity;
    private String recipientName;
    private String contactNumber;
    private String deliveryAddress;
    private BigDecimal totalAmount;
    private Integer deliveryCost;
    private Boolean isStoreRated;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}
