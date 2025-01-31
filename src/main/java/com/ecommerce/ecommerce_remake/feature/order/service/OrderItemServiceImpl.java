package com.ecommerce.ecommerce_remake.feature.order.service;

import com.ecommerce.ecommerce_remake.common.dto.response.PageResponse;
import com.ecommerce.ecommerce_remake.common.util.Pagination;
import com.ecommerce.ecommerce_remake.common.util.mapper.EntityToModelMapper;
import com.ecommerce.ecommerce_remake.feature.cart.model.Cart;
import com.ecommerce.ecommerce_remake.feature.cart.model.CartItem;
import com.ecommerce.ecommerce_remake.feature.cart.repository.CartItemRepository;
import com.ecommerce.ecommerce_remake.feature.inventory.model.Inventory;
import com.ecommerce.ecommerce_remake.feature.order.dto.OrderItemResponse;
import com.ecommerce.ecommerce_remake.feature.order.enums.OrderStatus;
import com.ecommerce.ecommerce_remake.feature.order.model.Order;
import com.ecommerce.ecommerce_remake.feature.order.model.OrderItem;
import com.ecommerce.ecommerce_remake.feature.order.model.OrderModel;
import com.ecommerce.ecommerce_remake.feature.order.repository.OrderItemRepository;
import com.ecommerce.ecommerce_remake.feature.order.repository.OrderRepository;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService{

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final Pagination pagination;

    private EntityToModelMapper<Order, OrderModel> entityToModelMapper = new EntityToModelMapper<>(OrderModel.class);
    @Override
    public OrderItemResponse getUserOrders(Pageable pageable, OrderStatus status) {
        User user = userService.getCurrentAuthenticatedUser();
        Page<Order> orders = orderRepository.findByUserAndStatus(user, status, pageable);
        PageResponse pageResponse = pagination.getPagination(orders);
        List<OrderModel> orderModelList = this.getOrderItems(orders);
        return new OrderItemResponse(orderModelList, pageResponse);
    }
    @Transactional
    @Override
    public void buyAgain(Integer orderId) {
        User user = userService.getCurrentAuthenticatedUser();
        Cart cart = user.getCart();

        List<OrderItem> orderItems = orderItemRepository.findAllByOrder_OrderId(orderId);

        for(OrderItem orderItem : orderItems){
            Inventory inventory = orderItem.getInventory();
            Optional<CartItem> existingCartItem = cartItemRepository.findByCartAndInventory(cart, inventory);

            CartItem cartItem;

            if(existingCartItem.isPresent()){
                cartItem = existingCartItem.get();
                cartItemRepository.save(cartItem);
            }else {
                cartItem = new CartItem(1, cart, inventory);
                cartItemRepository.save(cartItem);
            }
        }
    }

    private List<OrderModel> getOrderItems(Page<Order> orders){
        return orders.stream()
                .map(entityToModelMapper::map)
                .toList();
    }
}
