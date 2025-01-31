package com.ecommerce.ecommerce_remake.feature.favorites.controller;

import com.ecommerce.ecommerce_remake.feature.cart.dto.IdSetRequest;
import com.ecommerce.ecommerce_remake.feature.favorites.dto.FavoriteResponse;
import com.ecommerce.ecommerce_remake.feature.favorites.service.FavoritesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class FavoritesController {

    private final FavoritesService favoritesService;

    @PutMapping("/{productId}/favorite-add")
    @ResponseStatus(HttpStatus.OK)
    public void addProductToFavorites(@PathVariable("productId") String productId){
        favoritesService.addProductToFavorites(productId);
    }
    @PostMapping("/favorite-add")
    @ResponseStatus(HttpStatus.OK)
    public void addProductsToFavorites(@RequestBody IdSetRequest request){
        log.info("Received request to add cart items to favorites. IDs: {}", request.getIds());
        favoritesService.addProductsToFavorites(request);
    }
    @GetMapping("/{productId}/favorite-status")
    @ResponseStatus(HttpStatus.OK)
    public FavoriteResponse getFavoriteStatus(@PathVariable("productId") String productId){
        return favoritesService.getFavoriteStatus(productId);
    }
}
