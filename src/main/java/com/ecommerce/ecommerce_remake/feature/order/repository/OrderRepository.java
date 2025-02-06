package com.ecommerce.ecommerce_remake.feature.order.repository;

import com.ecommerce.ecommerce_remake.feature.order.enums.OrderStatus;
import com.ecommerce.ecommerce_remake.feature.order.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE o.userId = :userId AND (:status IS NULL OR o.orderStatus = :status)")
    Page<Order> getOrdersByUserAndStatus (@Param("userId") Integer userId, @Param("status") OrderStatus status, Pageable pageable);
    @Query("SELECT o FROM Order o WHERE o.store.storeId = :storeId AND (:status IS NULL OR o.orderStatus = :status)")
    Page<Order> getOrdersByStoreAndStatus(@Param("storeId") Integer storeId, @Param("status") OrderStatus status, Pageable pageable);
    List<Order> findAllByUserIdAndStore_StoreId(Integer userId, Integer storeId);
}
