package com.ecommerce.ecommerce_remake.web.exception;

public class LoginAttemptException extends RuntimeException{

    public LoginAttemptException(int expirationTime) {
//        super(String.format("Account is locked for %s minutes due to failed logins.", expirationTime));
        super(String.format("Too many failed login attempts. Please try again in %s minutes", expirationTime));
    }
}
