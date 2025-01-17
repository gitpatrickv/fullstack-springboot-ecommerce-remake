package com.ecommerce.ecommerce_remake.feature.order.service;

import com.ecommerce.ecommerce_remake.feature.order.dto.OrderItemResponse;

public interface OrderItemService {
    OrderItemResponse getAllOrderItems(int pageNo, int pageSize);
}
