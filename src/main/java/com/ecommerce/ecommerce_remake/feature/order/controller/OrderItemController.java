package com.ecommerce.ecommerce_remake.feature.order.controller;

import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.feature.order.enums.OrderStatus;
import com.ecommerce.ecommerce_remake.feature.order.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ecommerce.ecommerce_remake.common.util.PageableUtils.createPaginationAndSorting;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderItemController {

    private final OrderItemService orderItemService;
    @GetMapping
    public ResponseEntity<GetAllResponse> getOrders(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                    @RequestParam(value = "sortBy", defaultValue = "lastModified") String sortBy,
                                                    @RequestParam(value = "sortDirection", defaultValue = "DESC") String sortDirection,
                                                    @RequestParam(value = "userId", required = false) Integer userId,
                                                    @RequestParam(value = "storeId", required = false) Integer storeId,
                                                    @RequestParam(value = "status", required = false) OrderStatus status) {
        Pageable pageable = createPaginationAndSorting(pageNo, pageSize, sortBy, sortDirection);

        if(userId == null && storeId == null) {
            throw new IllegalArgumentException("GetOrders: User Id or Store Id must be provided");
        }

        GetAllResponse getAllResponse = userId != null
                ? orderItemService.getUserOrders(pageable, status, userId)
                : orderItemService.getStoreOrders(pageable, status, storeId);

        log.info("GetOrders - UserId: {}, StoreId: {}, Status: {} - Orders Found: {}",
                userId, storeId, status != null ? status : "ALL", getAllResponse.getModels().size());

        return ResponseEntity.ok().body(getAllResponse);
    }

    @PostMapping("/{orderId}/{cartId}/add")
    public void buyAgain(@PathVariable("orderId") Integer orderId, @PathVariable("cartId") Integer cartId){
        log.info("User initiated a repurchase request.");
        orderItemService.buyAgain(orderId, cartId);
    }
}
