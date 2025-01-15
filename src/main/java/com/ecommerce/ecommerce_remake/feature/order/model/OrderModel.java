package com.ecommerce.ecommerce_remake.feature.order.model;

import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.feature.order.enums.PaymentMethod;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {

    private Integer orderId;
    private Integer itemQuantity;
    private String recipientName;
    private String contactNumber;
    private String deliveryAddress;
    private BigDecimal totalAmount;
    private Integer deliveryCost;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private Status orderStatus;

    private List<OrderItemModel> orderItems = new ArrayList<>();
}
