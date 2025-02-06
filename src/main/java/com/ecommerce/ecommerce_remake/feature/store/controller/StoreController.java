package com.ecommerce.ecommerce_remake.feature.store.controller;

import com.ecommerce.ecommerce_remake.feature.store.dto.CountResponse;
import com.ecommerce.ecommerce_remake.feature.store.model.StoreModel;
import com.ecommerce.ecommerce_remake.feature.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
@Slf4j
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<StoreModel> getUserStore() {
        StoreModel storeModel = storeService.getUserStore();
        if(storeModel != null){
            log.info("GetUserStore - Get Response: 200 - Store data: {}", storeModel);
            return new ResponseEntity<>(storeModel, HttpStatus.OK);
        } else {
            log.info("Get Response: 404 - Store data not found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    public void uploadStoreAvatar(@RequestParam(value = "file") MultipartFile file){
        storeService.uploadStoreAvatar(file);
    }

    @GetMapping("/{storeId}/store-metrics")
    public ResponseEntity<CountResponse> getStoreMetrics(@PathVariable("storeId") Integer storeId){
        CountResponse countResponse = storeService.getStoreMetrics(storeId);
        log.info("Store ID {} has {} followers and {} products", storeId, countResponse.getFollowerCount(), countResponse.getProductCount());
        return ResponseEntity.ok().body(countResponse);
    }
}
