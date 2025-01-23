package com.ecommerce.ecommerce_remake.web.exception;

public class ReviewValidationException extends RuntimeException{
    public ReviewValidationException() {
        super("You have already submitted a review for this product.");
    }
}
