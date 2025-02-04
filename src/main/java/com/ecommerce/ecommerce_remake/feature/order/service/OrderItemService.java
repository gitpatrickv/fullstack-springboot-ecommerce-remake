package com.ecommerce.ecommerce_remake.feature.order.service;

import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.feature.order.enums.OrderStatus;
import org.springframework.data.domain.Pageable;

public interface OrderItemService {
    GetAllResponse getUserOrders(Pageable pageable, OrderStatus status, Integer userId);
    GetAllResponse getStoreOrders(Pageable pageable, OrderStatus status, Integer storeId);
    void buyAgain(Integer orderId, Integer cartId);
}
