package com.ecommerce.ecommerce_remake.feature.order.service;

import com.ecommerce.ecommerce_remake.feature.order.dto.OrderItemResponse;
import com.ecommerce.ecommerce_remake.feature.order.enums.OrderStatus;

public interface OrderItemService {
    OrderItemResponse getUserOrders(int pageNo, int pageSize, OrderStatus status);
}
