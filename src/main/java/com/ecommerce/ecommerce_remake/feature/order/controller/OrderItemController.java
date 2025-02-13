package com.ecommerce.ecommerce_remake.feature.order.controller;

import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.feature.order.enums.OrderStatus;
import com.ecommerce.ecommerce_remake.feature.order.service.OrderItemService;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
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
    private final UserService userService;
    @GetMapping
    public ResponseEntity<GetAllResponse> getOrders(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                    @RequestParam(value = "sortBy", defaultValue = "lastModified") String sortBy,
                                                    @RequestParam(value = "sortDirection", defaultValue = "DESC") String sortDirection,
                                                    @RequestParam(value = "isSellerPage") boolean isSellerPage,
                                                    @RequestParam(value = "status", required = false) OrderStatus status) {
        Pageable pageable = createPaginationAndSorting(pageNo, pageSize, sortBy, sortDirection);
        Integer storeId = userService.getStoreId();
        Integer userId = userService.getUserId();

        GetAllResponse getAllResponse = isSellerPage
                ? orderItemService.getStoreOrders(pageable, status, storeId)
                : orderItemService.getUserOrders(pageable, status, userId);

        log.info("GetOrders - isSellerPage: {} - Status: {} - Orders Found: {}",
                isSellerPage, status != null ? status : "ALL", getAllResponse.getModels().size());

        return ResponseEntity.ok().body(getAllResponse);
    }

    @PostMapping("/{orderId}/add")
    public void buyAgain(@PathVariable("orderId") Integer orderId){
        log.info("User initiated a repurchase request.");
        orderItemService.buyAgain(orderId);
    }
}
