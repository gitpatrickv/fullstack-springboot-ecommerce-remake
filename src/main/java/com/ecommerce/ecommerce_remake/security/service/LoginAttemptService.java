package com.ecommerce.ecommerce_remake.security.service;

public interface LoginAttemptService {

    void handleLoginAttempt(String email);
    void resetAttempt(String email);
}
