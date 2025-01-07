package com.ecommerce.ecommerce_remake.feature.store.controller;

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
            log.info("Get Response: 200 - returning store data {}", storeModel);
            return new ResponseEntity<>(storeModel, HttpStatus.OK);
        } else {
            log.info("Get Response: 200 - Store data not found");
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    public void uploadStoreAvatar(@RequestParam(value = "file") MultipartFile file){
        log.info("Received the request to upload the store avatar.");
        storeService.uploadStoreAvatar(file);
        log.info("The request to upload the store's avatar was handled.");
    }
}
