package com.ecommerce.ecommerce_remake.security.controller;


import com.ecommerce.ecommerce_remake.feature.user.model.UserModel;
import com.ecommerce.ecommerce_remake.security.dto.request.LoginRequest;
import com.ecommerce.ecommerce_remake.security.dto.response.LoginResponse;
import com.ecommerce.ecommerce_remake.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = authService.login(loginRequest);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public  ResponseEntity<LoginResponse> registerUser(@RequestBody @Valid UserModel userModel){
        LoginResponse loginResponse = authService.registerUser(userModel);
        return new ResponseEntity<>(loginResponse, HttpStatus.CREATED);
    }
}
