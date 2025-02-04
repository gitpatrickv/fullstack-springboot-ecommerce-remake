package com.ecommerce.ecommerce_remake.feature.cart.service;

import com.ecommerce.ecommerce_remake.common.util.mapper.EntityToModelMapper;
import com.ecommerce.ecommerce_remake.feature.cart.dto.CartItemsResponse;
import com.ecommerce.ecommerce_remake.feature.cart.dto.IdSetRequest;
import com.ecommerce.ecommerce_remake.feature.cart.dto.StoreInfo;
import com.ecommerce.ecommerce_remake.feature.cart.model.CartItem;
import com.ecommerce.ecommerce_remake.feature.cart.model.CartItemModel;
import com.ecommerce.ecommerce_remake.feature.cart.repository.CartItemRepository;
import com.ecommerce.ecommerce_remake.feature.product.model.Product;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import com.ecommerce.ecommerce_remake.web.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CartItemServiceImpl implements CartItemService{

    private final CartItemRepository cartItemRepository;
    private final UserService userService;

    private EntityToModelMapper<CartItem, CartItemModel> entityToModelMapper = new EntityToModelMapper<>(CartItemModel.class);

    @Override
    public Optional<CartItem> findExistingCartItem(Integer inventoryId, Integer cartId) {
        return cartItemRepository.findItemIfExist(inventoryId,cartId);
    }

    @Override
    public List<CartItemsResponse> getAllCartItems(Integer cartId) {
        List<CartItem> cartItemList = cartItemRepository.findAllByCart_CartId(cartId);
        Map<StoreInfo, List<CartItemModel>> cartItemMap = this.groupCartItemsByStore(cartItemList);
        return this.fetchAllCartItems(cartItemMap);
    }

    @Override
    public void updateQuantity(Integer cartItemId, Integer newQuantity) {
        CartItem cartItem = this.findCartItemById(cartItemId);
        cartItem.setQuantity(newQuantity);
        cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteCartItemById(Integer cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void deleteAllSelectedCartItem(IdSetRequest request) {
        Set<Integer> itemIds = request.getIds();
        cartItemRepository.deleteAllByIdInBatch(itemIds);
    }

    @Override
    public CartItem findCartItemById(Integer id) {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Cart Item not found with ID: %s", id)));
    }

    private Map<StoreInfo, List<CartItemModel>> groupCartItemsByStore(List<CartItem> cartItemList){
        Map<StoreInfo, List<CartItemModel>> cartItemMap = new HashMap<>();
        for(CartItem cartItem : cartItemList){
            Product product = cartItem.getInventory().getProduct();
            String storeName = product.getStore().getStoreName();
            Integer storeId = product.getStore().getStoreId();
            Integer productId = product.getProductId();
            String productName = product.getProductName();
            String productImage = product.getProductImages().get(0).getProductImage();

            CartItemModel cartItemModel = entityToModelMapper.map(cartItem);
            cartItemModel.setProductId(productId);
            cartItemModel.setProductName(productName);
            cartItemModel.setProductImage(productImage);

            StoreInfo storeInfo = new StoreInfo(storeName, storeId);
            cartItemMap.computeIfAbsent(storeInfo, k -> new ArrayList<>()).add(cartItemModel);
        }
        return cartItemMap;
    }
    // converts the Map to a List because i prefer working with lists over maps for easier frontend iteration and access.
    private List<CartItemsResponse> fetchAllCartItems(Map<StoreInfo, List<CartItemModel>> cartItemMap){
        return cartItemMap
                .entrySet()
                .stream()
                .map(entry -> {
                    StoreInfo storeInfo = entry.getKey();
                    List<CartItemModel> cartItems = entry.getValue();
                    return new CartItemsResponse(storeInfo.getStoreName(), storeInfo.getStoreId(), cartItems);
                })
                .toList();
    }
}
