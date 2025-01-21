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
import com.ecommerce.ecommerce_remake.feature.order.dto.OrderRequest;
import com.ecommerce.ecommerce_remake.feature.order.dto.PaymentResponse;
import com.ecommerce.ecommerce_remake.feature.order.enums.OrderStatus;
import com.ecommerce.ecommerce_remake.feature.order.enums.PaymentMethod;
import com.ecommerce.ecommerce_remake.feature.order.enums.ReviewStatus;
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
                Inventory inventory = orderItem.getInventory();
                Product product = inventory.getProduct();

                log.info("Order cancellation initiated: adding {} quantity to inventory ID={}", orderedProductQuantity, inventory.getInventoryId());
                inventoryRepository.addInventoryStockOnOrderCancellation(inventory.getInventoryId(), orderedProductQuantity);

                log.info("Updating product sales: subtracting {} quantity from total sold for product ID={}", orderedProductQuantity, product.getProductId());
                productRepository.subtractTotalProductSoldOnOrderCancellation(product.getProductId(), orderedProductQuantity);
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
        Order order = Order.builder()
                .itemQuantity(cartItems.size())
                .recipientName(address.getFullName())
                .contactNumber(address.getContactNumber())
                .deliveryAddress(String.format("%s %s, %s", address.getStreetAddress(), address.getCity(), address.getPostCode()))
                .totalAmount(CartServiceImpl.calculateTotalAmount(cartItems))
                .deliveryCost(50)
                .paymentMethod(request.getPaymentMethod())
                .orderStatus(request.getPaymentMethod().equals(PaymentMethod.STRIPE_PAYMENT) ? OrderStatus.TO_SHIP : OrderStatus.TO_PAY)
                .user(user)
                .store(store)
                .build();
        return orderRepository.save(order);
    }

    private List<OrderItem> createAndSaveOrderItems(List<CartItem> cartItems, Order savedOrder) {
        return cartItems.stream().map(cartItem -> {
            Inventory inventory = cartItem.getInventory();
            Product product = inventory.getProduct();

            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .productImage(product.getProductImages().get(0).getProductImage())
                    .productPrice(inventory.getPrice())
                    .color(inventory.getColor())
                    .size(inventory.getSize())
                    .inventory(inventory)
                    .reviewStatus(ReviewStatus.TO_REVIEW)
                    .order(savedOrder)
                    .build();

            this.updateAndValidateInventoryQuantity(inventory, product, cartItem.getQuantity());

            return orderItemRepository.save(orderItem);
        }).toList();
    }

    private void updateAndValidateInventoryQuantity(Inventory inventory, Product product, int itemQuantity) {

        int availableStock  = inventory.getQuantity();
        if(itemQuantity > availableStock) {
            throw new OutOfStockException(String.format("Insufficient stock for product '%s', Please adjust the quantity.", inventory.getProduct().getProductName()));
        } else {
            log.info("Processing order: Subtracting {} from inventory ID={}", itemQuantity, inventory.getInventoryId());
            inventoryRepository.subtractInventoryStockOnOrder (inventory.getInventoryId(), itemQuantity);

            log.info("Updating product sales: Adding {} to total sold for product ID={}", itemQuantity, product.getProductId());
            productRepository.addTotalProductSoldOnOrder(product.getProductId(), itemQuantity);
        }
    }
}

