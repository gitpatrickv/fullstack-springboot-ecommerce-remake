package com.ecommerce.ecommerce_remake.feature.cart.controller;

import com.ecommerce.ecommerce_remake.common.dto.enums.ResponseCode;
import com.ecommerce.ecommerce_remake.common.dto.response.Response;
import com.ecommerce.ecommerce_remake.feature.cart.dto.AddToCartRequest;
import com.ecommerce.ecommerce_remake.feature.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;
    @PostMapping
    public ResponseEntity<String> addToCart(@RequestBody AddToCartRequest request){
        log.info("Received the request to add a new items to the cart");
        Response response = cartService.addToCart(request);
        if(response.getResponseCode().equals(ResponseCode.RESP_SUCCESS)) {
            log.info("POST Response 200 - Product with ID={} was successfully added to the cart.", request.getProductId());
            return ResponseEntity.ok().body(response.getResponseDescription());
        } else {
            return ResponseEntity.badRequest().body(response.getResponseDescription());
        }
    }
}
