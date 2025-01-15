package com.ecommerce.ecommerce_remake.feature.order.repository;

import com.ecommerce.ecommerce_remake.feature.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
