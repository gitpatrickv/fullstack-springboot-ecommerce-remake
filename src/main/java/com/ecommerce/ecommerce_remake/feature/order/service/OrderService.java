package com.ecommerce.ecommerce_remake.feature.order.service;

import com.ecommerce.ecommerce_remake.feature.order.dto.OrderRequest;
import com.ecommerce.ecommerce_remake.feature.order.dto.PaymentResponse;
import com.ecommerce.ecommerce_remake.feature.order.enums.OrderStatus;
import com.ecommerce.ecommerce_remake.feature.order.model.Order;
import com.stripe.exception.StripeException;

public interface OrderService {

    PaymentResponse placeOrder(OrderRequest request) throws StripeException;
    void updateOrderStatus(Integer orderId, OrderStatus status);
    Order getOrderById(Integer orderId);
}
