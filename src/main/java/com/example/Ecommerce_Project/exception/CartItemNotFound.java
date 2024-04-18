package com.example.Ecommerce_Project.exception;

public class CartItemNotFound extends RuntimeException{
    public CartItemNotFound() {
    }

    public CartItemNotFound(String message) {
        super(message);
    }
}
