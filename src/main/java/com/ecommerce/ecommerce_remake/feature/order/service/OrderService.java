package com.ecommerce.ecommerce_remake.feature.order.service;

import com.ecommerce.ecommerce_remake.feature.order.dto.OrderRequest;

public interface OrderService {

    void placeOrder(OrderRequest request);
}
