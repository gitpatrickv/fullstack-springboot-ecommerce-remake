package com.ecommerce.ecommerce_remake.feature.cart.service;

import com.ecommerce.ecommerce_remake.common.dto.enums.ResponseCode;
import com.ecommerce.ecommerce_remake.common.dto.response.Response;
import com.ecommerce.ecommerce_remake.feature.cart.dto.AddToCartRequest;
import com.ecommerce.ecommerce_remake.feature.cart.model.Cart;
import com.ecommerce.ecommerce_remake.feature.cart.model.CartItem;
import com.ecommerce.ecommerce_remake.feature.cart.repository.CartItemRepository;
import com.ecommerce.ecommerce_remake.feature.inventory.model.Inventory;
import com.ecommerce.ecommerce_remake.feature.inventory.service.InventoryService;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService{

    private final UserService userService;
    private final CartItemService cartItemService;
    private final InventoryService inventoryService;
    private final CartItemRepository cartItemRepository;

    @Transactional
    @Override
    public Response addToCart(AddToCartRequest request) {
        User user = userService.getCurrentAuthenticatedUser();
        Cart cart = user.getCart();
        CartItem cartItem;
        Optional<CartItem> existingCartItem = cartItemService.findExistingCartItem(request.getInventoryId(), cart.getCartId());
        Optional<Inventory> inventory = inventoryService.findInventoryByProductId(request.getProductId());

        if(request.getQuantity() > inventory.get().getQuantity()){
            return new Response(ResponseCode.RESP_FAILURE,"Not enough stock available. Please adjust the quantity.");
        }

        if(existingCartItem.isPresent()){
            cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
            cartItemRepository.save(cartItem);
        } else {
            cartItem = new CartItem();
            cartItem.setQuantity(request.getQuantity());
            cartItem.setCart(cart);
            cartItem.setInventory(inventory.get());
            cartItemRepository.save(cartItem);
        }
        return new Response(ResponseCode.RESP_SUCCESS, String.format("'%s' has been successfully added to your shopping cart!", inventory.get().getProduct().getProductName()));
    }



}
