package com.ecommerce.ecommerce_remake.feature.favorites.service;

import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.feature.cart.dto.IdSetRequest;
import com.ecommerce.ecommerce_remake.feature.favorites.dto.FavoriteResponse;
import com.ecommerce.ecommerce_remake.feature.favorites.model.Favorites;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FavoritesService {
    void addProductToFavorites(String productId);
    void addProductsToFavorites(IdSetRequest request);
    FavoriteResponse getFavoriteStatus(String productId, Integer userId);
    Optional<Favorites> findFavoriteProduct(Integer productId, Integer userId);
    GetAllResponse getFavorites(Integer userId, Pageable pageable);

}
