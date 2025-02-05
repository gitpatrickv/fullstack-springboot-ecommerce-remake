package com.ecommerce.ecommerce_remake.feature.order.controller;

import com.ecommerce.ecommerce_remake.feature.order.dto.OrderRequest;
import com.ecommerce.ecommerce_remake.feature.order.dto.PaymentResponse;
import com.ecommerce.ecommerce_remake.feature.order.enums.OrderStatus;
import com.ecommerce.ecommerce_remake.feature.order.service.OrderService;
import com.ecommerce.ecommerce_remake.web.exception.OutOfStockException;
import com.ecommerce.ecommerce_remake.web.exception.ResourceNotFoundException;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest request) throws StripeException {
        log.info("Order request received, Cart item IDs={}", request.getIds());

        if(request.getIds().isEmpty()){
            log.warn("Order placement failed: No cart item IDs were provided in the request.");
            return ResponseEntity.badRequest().body("Please select items to place an order.");
        }

        try {
            PaymentResponse paymentResponse = orderService.placeOrder(request);
            return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
        } catch (OutOfStockException | ResourceNotFoundException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            log.error("An unexpected error occurred while placing the order: {}", ex.getMessage());
            return new ResponseEntity<>("An unexpected error occurred while processing your order. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/{orderId}/{status}")
    public void updateOrderStatus(@PathVariable("orderId") Integer orderId,
                                  @PathVariable("status") OrderStatus status){
        log.info("UpdateOrderStatus: Order ID={}, Order Status to = {}", orderId, status);
        orderService.updateOrderStatus(orderId, status);
    }

}