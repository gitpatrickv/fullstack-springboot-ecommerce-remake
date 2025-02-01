package com.ecommerce.ecommerce_remake.feature.store_following.controller;

import com.ecommerce.ecommerce_remake.feature.store_following.service.StoreFollowingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/following")
@RequiredArgsConstructor
public class StoreFollowingController {

    private final StoreFollowingService storeFollowingService;
}
