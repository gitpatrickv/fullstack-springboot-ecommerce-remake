package com.ecommerce.ecommerce_remake.feature.cart.controller;

import com.ecommerce.ecommerce_remake.feature.cart.dto.CartItemsResponse;
import com.ecommerce.ecommerce_remake.feature.cart.service.CartItemService;
import com.ecommerce.ecommerce_remake.web.exception.InvalidQuantityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart/item")
@RequiredArgsConstructor
@Slf4j
public class CartItemController {
    private final CartItemService cartItemService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CartItemsResponse> getAllCartItems(){
        return cartItemService.getAllCartItems();
    }

    @PutMapping("/{cartItemId}/{newQuantity}")
    public void updateQuantity(@PathVariable("cartItemId") Integer cartItemId,
                               @PathVariable("newQuantity") Integer newQuantity) {
        log.info("Received request to update quantity for CartItemId={} to new Quantity={} ", cartItemId, newQuantity);
        if (newQuantity <= 0) {
            log.warn("Attempted to set negative product quantity for cart item with id: {}.", cartItemId);
            throw new InvalidQuantityException();
        }
        cartItemService.updateQuantity(cartItemId,newQuantity);

    }
}
