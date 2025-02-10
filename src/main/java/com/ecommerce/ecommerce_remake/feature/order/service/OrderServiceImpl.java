package com.ecommerce.ecommerce_remake.feature.order.service;

import com.ecommerce.ecommerce_remake.common.dto.enums.Status;
import com.ecommerce.ecommerce_remake.feature.address.model.Address;
import com.ecommerce.ecommerce_remake.feature.address.service.AddressService;
import com.ecommerce.ecommerce_remake.feature.cart.model.CartItem;
import com.ecommerce.ecommerce_remake.feature.cart.repository.CartItemRepository;
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
import com.ecommerce.ecommerce_remake.feature.product_review.model.ProductReview;
import com.ecommerce.ecommerce_remake.feature.product_review.service.ProductReviewService;
import com.ecommerce.ecommerce_remake.feature.store.model.Store;
import com.ecommerce.ecommerce_remake.feature.store_rating.model.StoreRating;
import com.ecommerce.ecommerce_remake.feature.store_rating.service.StoreRatingService;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ecommerce.ecommerce_remake.feature.cart.service.CartServiceImpl.calculateTotalAmount;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService{

    private final CartItemRepository cartItemRepository;
    private final AddressService addressService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final PaymentService paymentService;
    private final ProductReviewService productReviewService;
    private final StoreRatingService storeRatingService;

    @Override
    public PaymentResponse placeOrder(OrderRequest request, Integer userId, Integer cartId) throws StripeException {
        Address address = addressService.findAddressByStatusAndUser(Status.ACTIVE, userId);

        List<CartItem> cartItemList = cartItemRepository.findByCart_CartIdAndCartItemIdIn(cartId, request.getIds());

        Map<Store, List<CartItem>> cartItemMap = groupItemsByStore(cartItemList);

        for (Map.Entry<Store, List<CartItem>> cartItem : cartItemMap.entrySet()) {
            List<CartItem> cartItems = cartItem.getValue();
            Store store = cartItem.getKey();
            Order savedOrder = this.createNewOrder(cartItems, address, userId, request, store);
            List<OrderItem> orderItems = this.createAndSaveOrderItems(cartItems, savedOrder);
            savedOrder.setOrderItems(orderItems);
        }
        cartItemRepository.deleteAllByIdInBatch(request.getIds());
        log.info("Deleted Cart Items: {}", request.getIds());

        return paymentService.paymentLink(cartItemList, request.getPaymentMethod());
    }

    @Override
    public void updateOrderStatus(Integer orderId, OrderStatus status) {
        Order order = this.getOrderById(orderId);
        order.setOrderStatus(status);
        orderRepository.save(order);

        if(status.equals(OrderStatus.CANCELLED)){
            this.revertInventoryOnCancellation(order);
        }

        if(status.equals(OrderStatus.COMPLETED)){
            this.validateIfReviewAlreadyExistForUser(order);
            productReviewService.updateOrderStatus(order);
            this.validateIfUserAlreadyRatedStore(order);
        }
    }

    @Override
    public Order getOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));
    }

    public static Map<Store, List<CartItem>> groupItemsByStore(List<CartItem> cartItemList) {
        return cartItemList.stream()
                .collect(Collectors.groupingBy(cartItem ->
                        cartItem.getInventory().getProduct().getStore()
                ));
    }

    private Order createNewOrder(List<CartItem> cartItems, Address address, Integer userId, OrderRequest request, Store store){
        Order order = Order.builder()
                .itemQuantity(cartItems.size())
                .recipientName(address.getFullName())
                .contactNumber(address.getContactNumber())
                .deliveryAddress(String.format("%s %s, %s", address.getStreetAddress(), address.getCity(), address.getPostCode()))
                .totalAmount(calculateTotalAmount(cartItems).add(BigDecimal.valueOf(50)))
                .deliveryCost(50)
                .isStoreRated(false)
                .paymentMethod(request.getPaymentMethod())
                .orderStatus(request.getPaymentMethod().equals(PaymentMethod.STRIPE_PAYMENT) ? OrderStatus.TO_SHIP : OrderStatus.TO_PAY)
                .userId(userId)
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

    private void revertInventoryOnCancellation(Order order){
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

    private void validateIfReviewAlreadyExistForUser(Order order){
        List<OrderItem> orderItems = order.getOrderItems();

        orderItems.forEach(orderItem -> {
            Integer productId = orderItem.getInventory().getProduct().getProductId();
            Optional<ProductReview> productReview = productReviewService.findReviewByUserAndProduct(order.getUserId(), productId);

            if(productReview.isPresent()){
                log.info("Setting REVIEWED status for order item with ID={}", orderItem.getOrderItemId());
                orderItem.setReviewStatus(ReviewStatus.REVIEWED);
                orderItemRepository.save(orderItem);
            }
        });
    }

    private void validateIfUserAlreadyRatedStore(Order order){
        Optional<StoreRating> storeReview = storeRatingService.findIfUserAlreadyRatedStore(order.getUserId(), order.getStore().getStoreId());

        if(storeReview.isPresent()){
            order.setIsStoreRated(true);
            orderRepository.save(order);
        }
    }


}

