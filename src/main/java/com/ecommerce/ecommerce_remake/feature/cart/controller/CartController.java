package com.ecommerce.ecommerce_remake.feature.cart.controller;

import com.ecommerce.ecommerce_remake.feature.cart.dto.AddToCartRequest;
import com.ecommerce.ecommerce_remake.feature.cart.dto.CartTotalResponse;
import com.ecommerce.ecommerce_remake.feature.cart.service.CartService;
import com.ecommerce.ecommerce_remake.web.exception.OutOfStockException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody @Valid AddToCartRequest request){
        log.info("Received the request to add a new items to the cart");
        try {
            cartService.addToCart(request);
            log.info("Add To Cart - Product added successfully - CartID={}, ProductID={}, Quantity={}", request.getCartId(), request.getProductId(), request.getQuantity());
            return ResponseEntity.ok().body("Item has been added to your Shopping Cart!");
        } catch(OutOfStockException ex) {
            log.warn("Add To Cart - failed to add product with ID={} to the cart", request.getProductId());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/{color}/{size}/add")
    public ResponseEntity<String> addToCartWithVariation(@RequestBody @Valid AddToCartRequest request,
                                                         @PathVariable("color") String color,
                                                         @PathVariable("size") String size) {
        log.info("Received the request to add a new items to the cart");
        try {
            cartService.addToCartWithVariation(request, color, size);
            log.info("Add To Cart - CartID={}, Product with ID={}, Quantity={} and variation (Color: {}, Size: {}) successfully added to the cart.",
                   request.getCartId(), request.getProductId(), request.getQuantity(), color, size);
            return ResponseEntity.ok().body("Item has been added to your Shopping Cart!");
        } catch(OutOfStockException ex) {
            log.warn("Add To Cart - Failed to add product with ID={} (Color: {}, Size: {}) to the cart",
                    request.getProductId(), color, size );
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{ids}/{cartId}/total")
    @ResponseStatus(HttpStatus.OK)
    public CartTotalResponse getCartTotal(@PathVariable("ids") Set<Integer> ids,@PathVariable("cartId") Integer cartId){

        if(ids.isEmpty()){
            log.warn("getCartTotal: No IDs provided. Unable to compute cart total.");
        }

        log.info("getCartTotal: Calculating total for cartId={} | Item count={} | Item IDs={}",cartId, ids.size(), ids);
        return cartService.getCartTotal(ids, cartId);
    }
}
