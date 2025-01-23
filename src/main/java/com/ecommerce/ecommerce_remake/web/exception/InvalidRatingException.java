package com.ecommerce.ecommerce_remake.web.exception;

public class InvalidRatingException extends RuntimeException{

    public InvalidRatingException() {
        super("Rating must be between 1 and 5");
    }
}
