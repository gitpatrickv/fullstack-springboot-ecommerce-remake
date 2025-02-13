package com.ecommerce.ecommerce_remake.feature.cart.service;

import com.ecommerce.ecommerce_remake.common.util.StrUtil;
import com.ecommerce.ecommerce_remake.feature.cart.dto.AddToCartRequest;
import com.ecommerce.ecommerce_remake.feature.cart.dto.CartTotalResponse;
import com.ecommerce.ecommerce_remake.feature.cart.model.Cart;
import com.ecommerce.ecommerce_remake.feature.cart.model.CartItem;
import com.ecommerce.ecommerce_remake.feature.cart.repository.CartItemRepository;
import com.ecommerce.ecommerce_remake.feature.cart.repository.CartRepository;
import com.ecommerce.ecommerce_remake.feature.inventory.model.Inventory;
import com.ecommerce.ecommerce_remake.feature.inventory.service.InventoryService;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import com.ecommerce.ecommerce_remake.web.exception.OutOfStockException;
import com.ecommerce.ecommerce_remake.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.ecommerce.ecommerce_remake.feature.order.service.OrderServiceImpl.groupItemsByStore;

@RequiredArgsConstructor
@Service
@Transactional
public class CartServiceImpl implements CartService{

    private final CartItemService cartItemService;
    private final InventoryService inventoryService;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final UserService userService;

    @Override
    public void addToCart(AddToCartRequest request) {
        Optional<Inventory> optionalInventory = inventoryService.findInventoryByProductId(request.getProductId());

        if(optionalInventory.isEmpty()){
            throw new ResourceNotFoundException(StrUtil.INVENTORY_NOT_FOUND);
        }

        Inventory inventory = optionalInventory.get();
        this.addProductsToCart(request, inventory);
    }

    @Override
    public void addToCartWithVariation(AddToCartRequest request, String color, String size) {
        Optional<Inventory> optionalInventory = inventoryService.findInventoryByColorAndSize(color, size, request.getProductId());

        if(optionalInventory.isEmpty()){
            throw new ResourceNotFoundException(StrUtil.INVENTORY_NOT_FOUND);
        }

        Inventory inventory = optionalInventory.get();
        this.addProductsToCart(request, inventory);
    }


    @Override
    public CartTotalResponse getCartTotal(Set<Integer> ids, Integer cartId) {
        List<CartItem> cartItemList = cartItemRepository.findByCart_CartIdAndCartItemIdIn(cartId, ids);
        BigDecimal cartTotal = calculateTotalAmount(cartItemList);
        int shippingFee = 50 * groupItemsByStore(cartItemList).size();
        int totalItems = this.calculateTotalProducts(cartItemList);
        BigDecimal totalAmount = cartTotal.add(BigDecimal.valueOf(shippingFee));
        return new CartTotalResponse(cartTotal ,totalItems, shippingFee, totalAmount);
    }

    @Override
    public Cart getCart() {
        return cartRepository.findById(userService.getUserCartId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found."));
    }

    private void addProductsToCart(AddToCartRequest request, Inventory inventory){
        Cart cart = this.getCart();

        Optional<CartItem> existingCartItem = cartItemService.findExistingCartItem(inventory.getInventoryId(), cart.getCartId());

        CartItem cartItem;

        int availableStock = inventory.getQuantity();

        this.validateStock(request.getQuantity(), availableStock);

        if(existingCartItem.isEmpty()){
            cartItem = new CartItem(request.getQuantity(), cart, inventory);
            cartItemRepository.save(cartItem);
        } else {
            cartItem = existingCartItem.get();
            if(cartItem.getQuantity() + request.getQuantity() > availableStock){
                throw new OutOfStockException(String.format("Insufficient stock. You already have %s in your cart", cartItem.getQuantity()));
            } else {
                cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
                cartItemRepository.save(cartItem);
            }
        }
    }

    private void validateStock(int requestQuantity, int availableStock){
        if(requestQuantity > availableStock){
            throw new OutOfStockException("Insufficient stock. Please adjust the quantity.");
        }
    }

    public static BigDecimal calculateTotalAmount(List<CartItem> cartItems){
        return cartItems.stream()
                .map(cartItem -> cartItem.getInventory().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Integer calculateTotalProducts(List<CartItem> cartItems){
        return cartItems.stream()
                .map(CartItem::getQuantity)
                .reduce(Integer::sum)
                .orElse(0);
    }
}
