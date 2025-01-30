package com.ecommerce.ecommerce_remake.security.service;
import com.ecommerce.ecommerce_remake.feature.user.model.UserModel;
import com.ecommerce.ecommerce_remake.security.dto.request.LoginRequest;
import com.ecommerce.ecommerce_remake.security.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequest);
    LoginResponse registerUser(UserModel userModel);
}
