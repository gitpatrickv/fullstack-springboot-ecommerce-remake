package com.ecommerce.ecommerce_remake.feature.order.service;

import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.feature.address.model.Address;
import com.ecommerce.ecommerce_remake.feature.address.service.AddressService;
import com.ecommerce.ecommerce_remake.feature.cart.model.Cart;
import com.ecommerce.ecommerce_remake.feature.cart.model.CartItem;
import com.ecommerce.ecommerce_remake.feature.cart.repository.CartItemRepository;
import com.ecommerce.ecommerce_remake.feature.cart.service.CartServiceImpl;
import com.ecommerce.ecommerce_remake.feature.inventory.model.Inventory;
import com.ecommerce.ecommerce_remake.feature.inventory.repository.InventoryRepository;
import com.ecommerce.ecommerce_remake.feature.inventory.service.InventoryService;
import com.ecommerce.ecommerce_remake.feature.order.dto.OrderRequest;
import com.ecommerce.ecommerce_remake.feature.order.dto.PaymentResponse;
import com.ecommerce.ecommerce_remake.feature.order.enums.OrderStatus;
import com.ecommerce.ecommerce_remake.feature.order.enums.PaymentMethod;
import com.ecommerce.ecommerce_remake.feature.order.model.Order;
import com.ecommerce.ecommerce_remake.feature.order.model.OrderItem;
import com.ecommerce.ecommerce_remake.feature.order.repository.OrderItemRepository;
import com.ecommerce.ecommerce_remake.feature.order.repository.OrderRepository;
import com.ecommerce.ecommerce_remake.feature.payment.service.PaymentService;
import com.ecommerce.ecommerce_remake.feature.product.model.Product;
import com.ecommerce.ecommerce_remake.feature.product.repository.ProductRepository;
import com.ecommerce.ecommerce_remake.feature.store.model.Store;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import com.ecommerce.ecommerce_remake.web.exception.OutOfStockException;
import com.ecommerce.ecommerce_remake.web.exception.ResourceNotFoundException;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService{

    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final AddressService addressService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final InventoryService inventoryService;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final PaymentService paymentService;

    @Override
    public PaymentResponse placeOrder(OrderRequest request) throws StripeException {
        User user = userService.getCurrentAuthenticatedUser();
        Cart cart = user.getCart();
        Address address = addressService.findAddressByStatusAndUser(Status.ACTIVE, user);

        List<CartItem> cartItemList = cartItemRepository.findByCartAndCartItemIdIn(cart, request.getIds());

        Map<Store, List<CartItem>> cartItemMap = this.groupItemsByStore(cartItemList);

        for (Map.Entry<Store, List<CartItem>> cartItem : cartItemMap.entrySet()) {
            List<CartItem> cartItems = cartItem.getValue();
            Store store = cartItem.getKey();
            Order savedOrder = this.createNewOrder(cartItems, address, user, request, store);
            List<OrderItem> orderItems = this.createAndSaveOrderItems(cartItems, savedOrder);
            savedOrder.setOrderItems(orderItems);
        }
        log.info("Deleting cart items...");
        cartItemRepository.deleteAllByIdInBatch(request.getIds());
        log.info("Deleted");
        BigDecimal totalAmount = CartServiceImpl.calculateTotalAmount(cartItemList);

        return paymentService.paymentLink(totalAmount.longValue(), request.getPaymentMethod());
    }

    @Override
    public void updateOrderStatus(Integer orderId, OrderStatus status) {
        Order order = this.getOrderById(orderId);
        order.setOrderStatus(status);
        orderRepository.save(order);

        if(status.equals(OrderStatus.CANCELLED)){
            List<OrderItem> orderItems = order.getOrderItems();

            orderItems.forEach(orderItem -> {
                int orderedProductQuantity = orderItem.getQuantity();
                log.info("Ordered product quantity: {}", orderedProductQuantity);
                Inventory inventory = orderItem.getInventory();
                this.updateInventoryOnOrderCancellation(inventory, orderedProductQuantity);
                Product product = inventory.getProduct();
                this.updateTotalProductSoldOnOrderCancellation(product, orderedProductQuantity);
            });
        }
    }

    @Override
    public Order getOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));
    }

    private Map<Store, List<CartItem>> groupItemsByStore(List<CartItem> cartItemList) {
        return cartItemList.stream()
                .collect(Collectors.groupingBy(cartItem ->
                        cartItem.getInventory().getProduct().getStore()
                ));
    }

    private Order createNewOrder(List<CartItem> cartItems, Address address, User user, OrderRequest request, Store store){
        Order order = new Order();
        order.setItemQuantity(cartItems.size());
        order.setRecipientName(address.getFullName());
        order.setContactNumber(address.getContactNumber());
        order.setDeliveryAddress(String.format("%s %s, %s", address.getStreetAddress(), address.getCity(), address.getPostCode()));
        order.setTotalAmount(CartServiceImpl.calculateTotalAmount(cartItems));
        order.setDeliveryCost(50);
        order.setPaymentMethod(request.getPaymentMethod());
        order.setOrderStatus(request.getPaymentMethod().equals(PaymentMethod.STRIPE_PAYMENT) ? OrderStatus.TO_SHIP : OrderStatus.TO_PAY);
        order.setUser(user);
        order.setStore(store);
        return orderRepository.save(order);
    }

    private List<OrderItem> createAndSaveOrderItems(List<CartItem> cartItems, Order savedOrder) {
        return cartItems.stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setProductName(cartItem.getInventory().getProduct().getProductName());
            orderItem.setProductImage(cartItem.getInventory().getProduct().getProductImages().get(0).getProductImage());
            orderItem.setProductPrice(cartItem.getInventory().getPrice());
            orderItem.setColor(cartItem.getInventory().getColor());
            orderItem.setSize(cartItem.getInventory().getSize());
            orderItem.setInventory(cartItem.getInventory());
            orderItem.setOrder(savedOrder);

            this.updateAndValidateInventoryQuantity(cartItem.getInventory().getInventoryId(), cartItem.getQuantity());

            return orderItemRepository.save(orderItem);
        }).toList();
    }

    private void updateAndValidateInventoryQuantity(int inventoryId, int itemQuantity) {
        Inventory inventory = inventoryService.findInventoryById(inventoryId);
        log.info("Updating inventory...");
        log.info("Cart item quantity = {}" , itemQuantity);
        log.info("Available inventory stock: {}, for inventory with ID={}", inventory.getQuantity(), inventory.getInventoryId());
        int availableStock  = inventory.getQuantity();

        if(itemQuantity > availableStock) {
            throw new OutOfStockException(String.format("Insufficient stock for product '%s', Please adjust the quantity.", inventory.getProduct().getProductName()));
        } else {
            inventory.setQuantity(availableStock - itemQuantity);
            Inventory savedInventory = inventoryRepository.save(inventory);
            log.info("Updated inventory stock: {}, for inventory with ID={}", savedInventory.getQuantity(), savedInventory.getInventoryId());
            this.updateTotalProductSold(inventory, itemQuantity);
        }
    }

    private void updateTotalProductSold(Inventory inventory, int quantityToAdd) {
        Product product = inventory.getProduct();
        log.info("Current product total sold: {}, for product with ID={}", product.getTotalSold(), product.getProductId());
        product.setTotalSold(product.getTotalSold() + quantityToAdd);
        Product savedProduct = productRepository.save(product);
        log.info("Updated product total sold: {}, for product with ID={}", savedProduct.getTotalSold(), savedProduct.getProductId());
    }

    private void updateInventoryOnOrderCancellation(Inventory inventory, Integer quantity){
        log.info("Before order cancellation, Available inventory stock: {}, for inventory with ID={}", inventory.getQuantity(), inventory.getInventoryId());
        inventory.setQuantity(inventory.getQuantity() + quantity);
        Inventory savedInventory = inventoryRepository.save(inventory);
        log.info("After order cancellation, Updated inventory stock: {}, for inventory with ID={}", savedInventory.getQuantity(), savedInventory.getInventoryId());
    }

    private void updateTotalProductSoldOnOrderCancellation(Product product, Integer quantityToSubtract){
        log.info("Before order cancellation, Current product total sold: {}, for product with ID={}", product.getTotalSold(), product.getProductId());
        product.setTotalSold(product.getTotalSold() - quantityToSubtract);
        Product savedProduct = productRepository.save(product);
        log.info("After order cancellation, Updated product total sold: {}, for product with ID={}", savedProduct.getTotalSold(), savedProduct.getProductId());
    }



}

