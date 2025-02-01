package com.ecommerce.ecommerce_remake.feature.store_following.controller;

import com.ecommerce.ecommerce_remake.feature.store_following.dto.Following;
import com.ecommerce.ecommerce_remake.feature.store_following.service.StoreFollowingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreFollowingController {

    private final StoreFollowingService storeFollowingService;

    @PutMapping("/{storeId}/follow")
    @ResponseStatus(HttpStatus.OK)
    public void followStore(@PathVariable("storeId") Integer storeId){
        storeFollowingService.followStore(storeId);
    }
    @GetMapping("/{userId}/{storeId}/follow-status")
    @ResponseStatus(HttpStatus.OK)
    public Following getFollowingStoreStatus(@PathVariable("userId") Integer userId,
                                             @PathVariable("storeId") Integer storeId){
        return storeFollowingService.getFollowingStoreStatus(userId,storeId);
    }
}
