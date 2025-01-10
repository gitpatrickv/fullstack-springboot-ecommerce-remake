package com.ecommerce.ecommerce_remake.feature.cart.service;

import com.ecommerce.ecommerce_remake.common.util.mapper.EntityToModelMapper;
import com.ecommerce.ecommerce_remake.feature.cart.dto.CartItemsResponse;
import com.ecommerce.ecommerce_remake.feature.cart.model.Cart;
import com.ecommerce.ecommerce_remake.feature.cart.model.CartItem;
import com.ecommerce.ecommerce_remake.feature.cart.model.CartItemModel;
import com.ecommerce.ecommerce_remake.feature.cart.repository.CartItemRepository;
import com.ecommerce.ecommerce_remake.feature.cart.repository.CartRepository;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import com.ecommerce.ecommerce_remake.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CartItemServiceImpl implements CartItemService{

    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final CartRepository cartRepository;

    private EntityToModelMapper<CartItem, CartItemModel> entityToModelMapper = new EntityToModelMapper<>(CartItemModel.class);

    @Override
    public Optional<CartItem> findExistingCartItem(Integer inventoryId, Integer cartId) {
        return cartItemRepository.findItemIfExist(inventoryId,cartId);
    }

    @Override
    public List<CartItemsResponse> getAllCartItems() {
        User user = userService.getCurrentAuthenticatedUser();
        Cart cart = user.getCart();

        List<CartItem> cartItemList = cartItemRepository.findByCart(cart);

        Map<String, List<CartItemModel>> cartItemMap = this.groupCartItemsByStore(cartItemList);

        return cartItemMap
                .entrySet()
                .stream()
                .map(entry -> new CartItemsResponse(entry.getKey(), entry.getValue()))
                .toList();
    }

    @Override
    public void updateQuantity(Integer cartItemId, Integer newQuantity) {
        CartItem cartItem = this.findCartItemById(cartItemId);
        cartItem.setQuantity(newQuantity);
        cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteCartItemById(Integer cartItemId) {
        User user = userService.getCurrentAuthenticatedUser();
        Cart cart = user.getCart();
        cartItemRepository.deleteById(cartItemId);
        cart.setTotalItems(cart.getTotalItems() - 1);
        cartRepository.save(cart);
    }

    @Override
    public CartItem findCartItemById(Integer id) {
        return cartItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("Cart Item not found with ID: %s", id)));
    }

    private Map<String, List<CartItemModel>> groupCartItemsByStore(List<CartItem> cartItemList){
        Map<String, List<CartItemModel>> cartItemMap = new HashMap<>();
        for(CartItem cartItem : cartItemList){
            String storeName = cartItem.getInventory().getProduct().getStore().getStoreName();
            String productName = cartItem.getInventory().getProduct().getProductName();
            String productImage = cartItem.getInventory().getProduct().getProductImages().get(0).getProductImage();

            CartItemModel cartItemModel = entityToModelMapper.map(cartItem);
            cartItemModel.setProductName(productName);
            cartItemModel.setProductImage(productImage);
            cartItemMap.computeIfAbsent(storeName, k -> new ArrayList<>()).add(cartItemModel);
        }
        return cartItemMap;
    }
}
