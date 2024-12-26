package com.ecommerce.ecommerce_remake.feature.user.controller;

import com.ecommerce.ecommerce_remake.feature.user.model.UserModel;
import com.ecommerce.ecommerce_remake.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping
    public UserModel getCurrentUserInfo() {
        return userService.getCurrentUserInfo();
    }
}
