package com.ecommerce.ecommerce_remake.web.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
