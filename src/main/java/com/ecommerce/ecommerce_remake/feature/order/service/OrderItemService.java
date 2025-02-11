package com.ecommerce.ecommerce_remake.feature.order.service;

import com.ecommerce.ecommerce_remake.feature.order.dto.OrderItemResponse;
import com.ecommerce.ecommerce_remake.feature.order.enums.OrderStatus;
import org.springframework.data.domain.Pageable;

public interface OrderItemService {
    OrderItemResponse getUserOrders(Pageable pageable, OrderStatus status, Integer userId);
    void buyAgain(Integer orderId, Integer cartId);
}
