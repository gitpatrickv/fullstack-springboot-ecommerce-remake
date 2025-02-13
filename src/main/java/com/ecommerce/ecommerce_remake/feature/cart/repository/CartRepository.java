package com.ecommerce.ecommerce_remake.feature.cart.repository;

import com.ecommerce.ecommerce_remake.feature.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByUser_UserId(Integer userId);
}
