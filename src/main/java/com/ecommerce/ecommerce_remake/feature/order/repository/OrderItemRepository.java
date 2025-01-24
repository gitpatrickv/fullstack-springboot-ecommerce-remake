package com.ecommerce.ecommerce_remake.feature.order.repository;

import com.ecommerce.ecommerce_remake.feature.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findAllByOrder_OrderId(Integer orderId);
    List<OrderItem> findAllByProductIdAndOrder_OrderId(Integer productId, Integer orderId);

}
