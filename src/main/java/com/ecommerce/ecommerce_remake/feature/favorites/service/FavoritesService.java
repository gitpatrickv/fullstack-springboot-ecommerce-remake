package com.ecommerce.ecommerce_remake.feature.favorites.service;

import com.ecommerce.ecommerce_remake.feature.cart.dto.IdSetRequest;
import com.ecommerce.ecommerce_remake.feature.favorites.dto.FavoriteResponse;
import com.ecommerce.ecommerce_remake.feature.favorites.model.Favorites;
import com.ecommerce.ecommerce_remake.feature.user.model.User;

import java.util.Optional;

public interface FavoritesService {
    void addProductToFavorites(String productId);
    void addProductsToFavorites(IdSetRequest request);
    FavoriteResponse getFavoriteStatus(String productId);
    Optional<Favorites> findFavoriteProduct(Integer productId, User user);
}
