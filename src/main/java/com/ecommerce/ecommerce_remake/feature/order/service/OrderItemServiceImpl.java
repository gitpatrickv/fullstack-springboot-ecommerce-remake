package com.ecommerce.ecommerce_remake.feature.order.service;

import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.common.dto.response.PageResponse;
import com.ecommerce.ecommerce_remake.common.util.Pagination;
import com.ecommerce.ecommerce_remake.common.util.mapper.EntityToModelMapper;
import com.ecommerce.ecommerce_remake.feature.cart.model.Cart;
import com.ecommerce.ecommerce_remake.feature.cart.model.CartItem;
import com.ecommerce.ecommerce_remake.feature.cart.repository.CartItemRepository;
import com.ecommerce.ecommerce_remake.feature.cart.service.CartService;
import com.ecommerce.ecommerce_remake.feature.inventory.model.Inventory;
import com.ecommerce.ecommerce_remake.feature.order.enums.OrderStatus;
import com.ecommerce.ecommerce_remake.feature.order.model.Order;
import com.ecommerce.ecommerce_remake.feature.order.model.OrderItem;
import com.ecommerce.ecommerce_remake.feature.order.model.OrderModel;
import com.ecommerce.ecommerce_remake.feature.order.repository.OrderItemRepository;
import com.ecommerce.ecommerce_remake.feature.order.repository.OrderRepository;
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

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final Pagination pagination;
    private final CartService cartService;

    private EntityToModelMapper<Order, OrderModel> entityToModelMapper = new EntityToModelMapper<>(OrderModel.class);
    @Override
    public GetAllResponse getUserOrders(Pageable pageable, OrderStatus status, Integer userId) {
        Page<Order> orders = orderRepository.getOrdersByUserAndStatus(userId, status, pageable);
        return this.getAllOrdersWithPagination(orders);
    }

    @Override
    public GetAllResponse getStoreOrders(Pageable pageable, OrderStatus status, Integer storeId) {
        Page<Order> orders = orderRepository.getOrdersByStoreAndStatus(storeId, status, pageable);
        return this.getAllOrdersWithPagination(orders);
    }

    @Transactional
    @Override
    public void buyAgain(Integer orderId, Integer cartId) {
        Cart cart = cartService.getCartById(cartId);

        List<OrderItem> orderItems = orderItemRepository.findAllByOrder_OrderId(orderId);

        orderItems.forEach(orderItem -> {
            Inventory inventory = orderItem.getInventory();
            Optional<CartItem> existingCartItem = cartItemRepository.findByCart_CartIdAndInventory(cart.getCartId(), inventory);

            CartItem cartItem;

            if(existingCartItem.isPresent()){
                cartItem = existingCartItem.get();
                cartItemRepository.save(cartItem);
            }else {
                cartItem = new CartItem(1, cart, inventory);
                cartItemRepository.save(cartItem);
            }
        });
    }

    private GetAllResponse getAllOrdersWithPagination(Page<Order> orders){
        PageResponse pageResponse = pagination.getPagination(orders);
        List<OrderModel> orderModelList = this.getOrderItems(orders);
        return new GetAllResponse(orderModelList, pageResponse);
    }

    private List<OrderModel> getOrderItems(Page<Order> orders){
        return orders.stream()
                .map(entityToModelMapper::map)
                .toList();
    }
}
