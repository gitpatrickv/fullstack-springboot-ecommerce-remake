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
@Transactional
public class CartServiceImpl implements CartService{

    private final UserService userService;
    private final CartItemService cartItemService;
    private final InventoryService inventoryService;
    private final CartItemRepository cartItemRepository;


    @Override
    public Response addToCart(AddToCartRequest request) {
        Optional<Inventory> optionalInventory = inventoryService.findInventoryByProductId(request.getProductId());
        Inventory inventory = optionalInventory.get();
        return this.addProductsToCart(request, inventory);
    }

    @Override
    public Response addToCartWithVariation(AddToCartRequest request, String color, String size) {
        Optional<Inventory> optionalInventory = inventoryService.findInventoryByColorAndSize(color, size, request.getProductId());
        Inventory inventory = optionalInventory.get();
        return this.addProductsToCart(request, inventory);
    }

    private  Response addProductsToCart(AddToCartRequest request, Inventory inventory){
        User user = userService.getCurrentAuthenticatedUser();
        Cart cart = user.getCart();

        Optional<CartItem> existingCartItem = cartItemService.findExistingCartItem(inventory.getInventoryId(), cart.getCartId());

        CartItem cartItem;

        if(request.getQuantity() > inventory.getQuantity()){
            return new Response(ResponseCode.RESP_FAILURE,"Insufficient stock. Please adjust the quantity.");
        }

        if(existingCartItem.isPresent()){
            cartItem = existingCartItem.get();
            if(cartItem.getQuantity() + request.getQuantity() > inventory.getQuantity()){
                return new Response(ResponseCode.RESP_FAILURE, String.format("Insufficient stock. You already have %s in your cart", existingCartItem.get().getQuantity()));
            }
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
            cartItemRepository.save(cartItem);
        }
        else {
            cartItem = new CartItem();
            cartItem.setQuantity(request.getQuantity());
            cartItem.setCart(cart);
            cartItem.setInventory(inventory);
            cartItemRepository.save(cartItem);
        }
        return new Response(ResponseCode.RESP_SUCCESS, String.format("Added %s '%s' to your shopping cart!",request.getQuantity(), inventory.getProduct().getProductName()));
    }


}
