package com.ecommerce.ecommerce_remake.feature.cart.controller;

import com.ecommerce.ecommerce_remake.feature.cart.dto.CartItemsResponse;
import com.ecommerce.ecommerce_remake.feature.cart.dto.IdSetRequest;
import com.ecommerce.ecommerce_remake.feature.cart.service.CartItemService;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import com.ecommerce.ecommerce_remake.web.exception.InvalidQuantityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class CartItemController {
    private final CartItemService cartItemService;
    private final UserService userService;

    @GetMapping("/cart-items")
    public List<CartItemsResponse> getAllCartItems(){
        Integer cartId = userService.getUserCartId();
        log.info("GetAllCartItems - CartId : {}", cartId);
        return cartItemService.getAllCartItems(cartId);
    }

    @PutMapping("/{cartItemId}/{newQuantity}/quantity")
    @ResponseStatus(HttpStatus.OK)
    public void updateQuantity(@PathVariable("cartItemId") Integer cartItemId,
                               @PathVariable("newQuantity") Integer newQuantity) {
        log.info("Update Quantity for CartItemId={} to new Quantity={} ", cartItemId, newQuantity);
        if (newQuantity <= 0) {
            log.warn("Attempted to set negative product quantity for cart item with id: {}.", cartItemId);
            throw new InvalidQuantityException();
        }
        cartItemService.updateQuantity(cartItemId,newQuantity);
    }

    @DeleteMapping("/{cartItemId}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCartItemById(@PathVariable("cartItemId") Integer cartItemId) {
        log.info("Received request to delete cart item with id = {}", cartItemId);
        cartItemService.deleteCartItemById(cartItemId);
        log.info("cart item with id = {} is successfully deleted", cartItemId);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAllSelectedCartItem(@RequestBody IdSetRequest request) {
        log.info("Received request to delete cart items for ids = {}", request.getIds());
        if(request.getIds().isEmpty()){
            log.warn("Cart item deletion failed due to missing item IDs.");
            return new ResponseEntity<>("Please select items to delete.", HttpStatus.BAD_REQUEST);
        } else {
            log.info("number of items to be deleted: {} ", request.getIds().size());
            cartItemService.deleteAllSelectedCartItem(request);
            log.info("cart item for ids = {} is successfully deleted", request.getIds());
            return new ResponseEntity<>("Selected cart items have been deleted successfully.", HttpStatus.OK);
        }
    }
}
