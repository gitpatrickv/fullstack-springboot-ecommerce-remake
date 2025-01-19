package com.ecommerce.ecommerce_remake.feature.order.service;

import com.ecommerce.ecommerce_remake.common.dto.response.PageResponse;
import com.ecommerce.ecommerce_remake.common.util.Pagination;
import com.ecommerce.ecommerce_remake.common.util.mapper.EntityToModelMapper;
import com.ecommerce.ecommerce_remake.feature.order.dto.OrderItemResponse;
import com.ecommerce.ecommerce_remake.feature.order.enums.OrderStatus;
import com.ecommerce.ecommerce_remake.feature.order.model.Order;
import com.ecommerce.ecommerce_remake.feature.order.model.OrderModel;
import com.ecommerce.ecommerce_remake.feature.order.repository.OrderRepository;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService{

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final Pagination pagination;

    private EntityToModelMapper<Order, OrderModel> entityToModelMapper = new EntityToModelMapper<>(OrderModel.class);
    @Override
    public OrderItemResponse getUserOrders(int pageNo, int pageSize, OrderStatus status) {
        User user = userService.getCurrentAuthenticatedUser();
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<Order> orders = orderRepository.findByUserAndStatus(user, status, pageable);
        PageResponse pageResponse = pagination.getPagination(orders);
        List<OrderModel> orderModelList = this.getOrderItems(orders);
        return new OrderItemResponse(orderModelList, pageResponse);
    }

    private List<OrderModel> getOrderItems(Page<Order> orders){
        return orders.stream()
                .map(entityToModelMapper::map)
                .toList();
    }
}
