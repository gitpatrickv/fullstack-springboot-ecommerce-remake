package com.ecommerce.ecommerce_remake.feature.cart.service;

import com.ecommerce.ecommerce_remake.feature.cart.model.CartItem;
import com.ecommerce.ecommerce_remake.feature.cart.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService{

    private final CartItemRepository cartItemRepository;
    @Override
    public Optional<CartItem> findExistingCartItem(Integer inventoryId, Integer cartId) {
        return cartItemRepository.findItemIfExist(inventoryId,cartId);
    }
}
