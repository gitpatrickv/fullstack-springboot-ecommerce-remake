package com.ecommerce.ecommerce_remake.feature.cart.controller;

import com.ecommerce.ecommerce_remake.common.dto.enums.ResponseCode;
import com.ecommerce.ecommerce_remake.common.dto.response.Response;
import com.ecommerce.ecommerce_remake.feature.cart.dto.AddToCartRequest;
import com.ecommerce.ecommerce_remake.feature.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;
    @PostMapping
    public ResponseEntity<String> addToCart(@RequestBody @Valid AddToCartRequest request){
        log.info("Received the request to add a new items to the cart");
        Response response = cartService.addToCart(request);
        if(response.getResponseCode().equals(ResponseCode.RESP_SUCCESS)) {
            log.info("POST Response 200 - Product with ID={} was successfully added to the cart.", request.getProductId());
            return ResponseEntity.ok().body(response.getResponseDescription());
        } else {
            log.warn("POST Response 400 - failed to add product with ID={} to the cart", request.getProductId());
            return ResponseEntity.badRequest().body(response.getResponseDescription());
        }
    }

    @PostMapping("/{color}/{size}")
    public ResponseEntity<String> addToCartWithVariation(@RequestBody @Valid AddToCartRequest request, @PathVariable("color") String color, @PathVariable("size") String size) {
        log.info("Received the request to add a new items to the cart");
        Response response = cartService.addToCartWithVariation(request, color, size);
        if(response.getResponseCode().equals(ResponseCode.RESP_SUCCESS)) {
            log.info("Product with ID={} and variation (Color: {}, Size: {}) successfully added to the cart.",
                    request.getProductId(), color, size);
            return ResponseEntity.ok().body(response.getResponseDescription());
        } else {
            log.warn("Failed to add product with ID={} (Color: {}, Size: {}) to the cart",
                    request.getProductId(), color, size );
            return ResponseEntity.badRequest().body(response.getResponseDescription());
        }
    }
}
