package com.ecommerce.ecommerce_remake.feature.favorites.service;

import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.common.dto.response.PageResponse;
import com.ecommerce.ecommerce_remake.common.util.Pagination;
import com.ecommerce.ecommerce_remake.feature.cart.dto.IdSetRequest;
import com.ecommerce.ecommerce_remake.feature.cart.model.Cart;
import com.ecommerce.ecommerce_remake.feature.cart.model.CartItem;
import com.ecommerce.ecommerce_remake.feature.cart.repository.CartItemRepository;
import com.ecommerce.ecommerce_remake.feature.favorites.dto.FavoriteResponse;
import com.ecommerce.ecommerce_remake.feature.favorites.model.Favorites;
import com.ecommerce.ecommerce_remake.feature.favorites.repository.FavoritesRepository;
import com.ecommerce.ecommerce_remake.feature.product.dto.ProductInfoResponse;
import com.ecommerce.ecommerce_remake.feature.product.model.Product;
import com.ecommerce.ecommerce_remake.feature.product.service.ProductService;
import com.ecommerce.ecommerce_remake.feature.user.model.User;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoritesServiceImpl implements FavoritesService{

    private final UserService userService;
    private final ProductService productService;
    private final FavoritesRepository favoritesRepository;
    private final CartItemRepository cartItemRepository;
    private final Pagination pagination;

    @Override
    public void addProductToFavorites(String productId) {
        Integer id = Integer.parseInt(productId);
        User user = userService.getCurrentAuthenticatedUser();
        Optional<Favorites> existingFavorite = this.findFavoriteProduct(id, user.getUserId());

        if(existingFavorite.isPresent()){
            favoritesRepository.deleteByProduct_ProductIdAndUser_UserId(id, user.getUserId());
        } else {
            this.createNewFavorite(id, user);
        }
    }

    @Override
    public void addProductsToFavorites(IdSetRequest request) {
        User user = userService.getCurrentAuthenticatedUser();
        Cart cart = user.getCart();

        List<CartItem> cartItems = cartItemRepository.findByCart_CartIdAndCartItemIdIn(cart.getCartId(), request.getIds());

        cartItems.forEach(cartItem -> {
            Integer productId = cartItem.getInventory().getProduct().getProductId();
            Optional<Favorites> existingFavorite = this.findFavoriteProduct(productId, user.getUserId());

            if(existingFavorite.isPresent()){
                cartItemRepository.deleteById(cartItem.getCartItemId());
            } else {
                this.createNewFavorite(productId, user);
                cartItemRepository.deleteById(cartItem.getCartItemId());
            }
        });
    }

    @Override
    public FavoriteResponse getFavoriteStatus(String productId, Integer userId) {
        Integer id = Integer.parseInt(productId);
        Optional<Favorites> favorites = this.findFavoriteProduct(id, userId);
        return new FavoriteResponse(favorites.isPresent());
    }

    @Override
    public GetAllResponse getFavorites(Integer userId, Pageable pageable) {
        Page<Favorites> favorites = favoritesRepository.findAllByUser_UserId(userId, pageable);
        PageResponse pageResponse = pagination.getPagination(favorites);
        List<ProductInfoResponse> productModels = this.getProductInfo(favorites);
        return new GetAllResponse(productModels, pageResponse);
    }

    @Override
    public Optional<Favorites> findFavoriteProduct(Integer productId, Integer userId) {
        return favoritesRepository.findByProduct_ProductIdAndUser_UserId(productId, userId);
    }

    private List<ProductInfoResponse> getProductInfo(Page<Favorites> favorites) {
        return favorites.stream()
                .map(favorite -> {
                    Product product = favorite.getProduct();
                    return productService.mapProductInfo(product);
                }).toList();
    }

    private void createNewFavorite(Integer productId, User user){
        Product product = productService.getProductById(productId);
        Favorites favorites = new Favorites(user, product);
        favoritesRepository.save(favorites);
    }
}
