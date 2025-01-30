package com.ecommerce.ecommerce_remake.web.exception;

import lombok.Getter;

@Getter
public class InvalidQuantityException extends RuntimeException {
    public InvalidQuantityException() {
        super("Quantity must be greater than zero");
    }
}
