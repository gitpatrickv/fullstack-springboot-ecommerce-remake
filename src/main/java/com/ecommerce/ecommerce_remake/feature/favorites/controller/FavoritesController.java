package com.ecommerce.ecommerce_remake.feature.favorites.controller;

import com.ecommerce.ecommerce_remake.common.dto.response.GetAllResponse;
import com.ecommerce.ecommerce_remake.feature.cart.dto.IdSetRequest;
import com.ecommerce.ecommerce_remake.feature.favorites.dto.FavoriteResponse;
import com.ecommerce.ecommerce_remake.feature.favorites.service.FavoritesService;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
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
    private final UserService userService;

    @PutMapping("/{productId}/favorite-add")
    @ResponseStatus(HttpStatus.OK)
    public void addProductToFavorites(@PathVariable("productId") String productId){
        Integer userId = userService.getUserId();
        favoritesService.addProductToFavorites(productId, userId);
    }
    @PostMapping("/favorite-add")
    @ResponseStatus(HttpStatus.OK)
    public void addProductsToFavorites(@RequestBody IdSetRequest request){
        Integer userId = userService.getUserId();
        Integer cartId = userService.getUserCartId();
        log.info("AddProductToFavorites - UserId : {}, CartId: {}, ProductIds: {}", userId, cartId, request.getIds());
        favoritesService.addProductsToFavorites(request, userId, cartId);
    }
    @GetMapping("/{productId}/favorite-status")
    @ResponseStatus(HttpStatus.OK)
    public FavoriteResponse getFavoriteStatus(@PathVariable("productId") String productId){
        Integer userId = userService.getUserId();
        return favoritesService.getFavoriteStatus(productId, userId);
    }

    @GetMapping("/favorites")
    public ResponseEntity<GetAllResponse> getFavorites(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                       @RequestParam(value = "sortBy", defaultValue = "createdDate") String sortBy,
                                                       @RequestParam(value = "sortDirection", defaultValue = "DESC") String sortDirection){
        Integer userId = userService.getUserId();
        Pageable pageable = createPaginationAndSorting(pageNo, pageSize, sortBy, sortDirection);
        GetAllResponse getAllResponse = favoritesService.getFavorites(userId, pageable);
        log.info("GetFavorites - GET Response: 200 - UserId: {}, Favorite Products : {}", userId, getAllResponse.getModels().size());
        return ResponseEntity.ok(getAllResponse);

    }
}
