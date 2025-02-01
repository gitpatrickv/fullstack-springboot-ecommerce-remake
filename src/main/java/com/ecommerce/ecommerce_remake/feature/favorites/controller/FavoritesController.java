package com.ecommerce.ecommerce_remake.feature.favorites.controller;

import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.feature.cart.dto.IdSetRequest;
import com.ecommerce.ecommerce_remake.feature.favorites.dto.FavoriteResponse;
import com.ecommerce.ecommerce_remake.feature.favorites.service.FavoritesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ecommerce.ecommerce_remake.common.util.PageableUtils.createPaginationAndSorting;

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
    @GetMapping("/{productId}/{userId}/favorite-status")
    @ResponseStatus(HttpStatus.OK)
    public FavoriteResponse getFavoriteStatus(@PathVariable("productId") String productId,
                                              @PathVariable("userId") Integer userId){
        return favoritesService.getFavoriteStatus(productId, userId);
    }

    @GetMapping("/{userId}/favorites")
    public ResponseEntity<GetAllResponse> getFavorites(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                       @RequestParam(value = "sortBy", defaultValue = "createdDate") String sortBy,
                                                       @RequestParam(value = "sortDirection", defaultValue = "DESC") String sortDirection,
                                                       @PathVariable("userId") Integer userId){
        Pageable pageable = createPaginationAndSorting(pageNo, pageSize, sortBy, sortDirection);
        GetAllResponse getAllResponse = favoritesService.getFavorites(userId, pageable);

        if(getAllResponse.getModels().isEmpty()){
            log.warn("GetFavorites - No data found");
        }
        log.info("GetFavorites - GET Response: 200 - Returning {} product records", getAllResponse.getModels().size());
        return ResponseEntity.ok(getAllResponse);

    }
}
