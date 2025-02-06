package com.ecommerce.ecommerce_remake.feature.store_following.controller;

import com.ecommerce.ecommerce_remake.feature.store_following.dto.Following;
import com.ecommerce.ecommerce_remake.feature.store_following.dto.StoreFollowListResponse;
import com.ecommerce.ecommerce_remake.feature.store_following.service.StoreFollowingService;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
public class StoreFollowingController {

    private final StoreFollowingService storeFollowingService;
    private final UserService userService;

    @PutMapping("/{storeId}/follow")
    @ResponseStatus(HttpStatus.OK)
    public void followStore(@PathVariable("storeId") Integer storeId){
        Integer userId = userService.getUserId();
        storeFollowingService.followStore(storeId, userId);
    }
    @GetMapping("/{storeId}/follow-status")
    @ResponseStatus(HttpStatus.OK)
    public Following getFollowingStoreStatus(@PathVariable("storeId") Integer storeId){
        Integer userId = userService.getUserId();
        return storeFollowingService.getFollowingStoreStatus(userId,storeId);
    }

    @GetMapping("/followed-stores")
    public ResponseEntity<List<StoreFollowListResponse>> getAllFollowedStores() {
        Integer userId = userService.getUserId();
        List<StoreFollowListResponse> followList = storeFollowingService.getAllFollowedStores(userId);
        log.info("GetAllFollowedStores - GET Response: 200 - UserId: {}, Followed Store: {} ", userId, followList.size());
        return ResponseEntity.ok(followList);
    }
}
