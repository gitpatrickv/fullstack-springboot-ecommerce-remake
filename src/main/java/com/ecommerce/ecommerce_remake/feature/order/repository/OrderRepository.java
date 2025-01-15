package com.ecommerce.ecommerce_remake.feature.order.repository;

import com.ecommerce.ecommerce_remake.feature.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
