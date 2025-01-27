package com.ecommerce.ecommerce_remake.feature.order.controller;

import com.ecommerce.ecommerce_remake.feature.order.dto.OrderItemResponse;
import com.ecommerce.ecommerce_remake.feature.order.enums.OrderStatus;
import com.ecommerce.ecommerce_remake.feature.order.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static com.ecommerce.ecommerce_remake.common.util.PageableUtils.createPaginationAndSorting;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderItemController {

    private final OrderItemService orderItemService;
    @GetMapping
    public OrderItemResponse getUserOrders(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                           @RequestParam(value = "sortBy", defaultValue = "lastModified") String sortBy,
                                           @RequestParam(value = "sortDirection", defaultValue = "DESC") String sortDirection,
                                           @RequestParam(value = "status", required = false) OrderStatus status) {
        log.info("Returning order items with status: {}", status != null ? status : "ALL");
        Pageable pageable = createPaginationAndSorting(pageNo, pageSize, sortBy, sortDirection);
        return orderItemService.getUserOrders(pageable, status);
    }
    @PostMapping("/{orderId}/add")
    public void buyAgain(@PathVariable("orderId") Integer orderId){
        log.info("User initiated a repurchase request.");
        orderItemService.buyAgain(orderId);
    }
}
