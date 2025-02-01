package com.ecommerce.ecommerce_remake.feature.user.controller;

import com.ecommerce.ecommerce_remake.feature.user.model.UserModel;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    @GetMapping
    public ResponseEntity<UserModel> getCurrentUserInfo() {
        log.info("fetching current user info");
        return ResponseEntity.ok(userService.getCurrentUserInfo());
    }
    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    public void uploadUserAvatar(@RequestParam(value = "file") MultipartFile file){
        log.info("Received the request to upload the user avatar.");
        userService.uploadUserAvatar(file);
        log.info("The request to upload the user's avatar was handled.");
    }
}
