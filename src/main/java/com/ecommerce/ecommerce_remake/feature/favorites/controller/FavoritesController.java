package com.ecommerce.ecommerce_remake.feature.favorites.controller;

import com.ecommerce.ecommerce_remake.feature.favorites.dto.FavoriteResponse;
import com.ecommerce.ecommerce_remake.feature.favorites.service.FavoritesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class FavoritesController {

    private final FavoritesService favoritesService;

    @PutMapping("/{productId}/favorite-add")
    public void addProductToFavorites(@PathVariable("productId") String productId){
        favoritesService.addProductToFavorites(productId);
    }
    @GetMapping("/{productId}/favorite-status")
    public FavoriteResponse getFavoriteStatus(@PathVariable("productId") String productId){
        return favoritesService.getFavoriteStatus(productId);
    }
}
