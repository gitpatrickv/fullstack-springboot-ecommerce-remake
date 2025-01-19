package com.ecommerce.ecommerce_remake.feature.order.controller;

import com.ecommerce.ecommerce_remake.feature.order.dto.OrderItemResponse;
import com.ecommerce.ecommerce_remake.feature.order.enums.OrderStatus;
import com.ecommerce.ecommerce_remake.feature.order.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderItemController {

    private final OrderItemService orderItemService;
    @GetMapping
    public OrderItemResponse getUserOrders(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                              @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                              @RequestParam(value = "status", required = false) OrderStatus status) {
        log.info("Returning order items with status: {}", status != null ? status : "ALL");
        return orderItemService.getUserOrders(pageNo, pageSize, status);
    }
    @PostMapping("/{orderId}/add")
    public void buyAgain(@PathVariable("orderId") Integer orderId){
        log.info("User initiated a repurchase request.");
        orderItemService.buyAgain(orderId);
    }
}
