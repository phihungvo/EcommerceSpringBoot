package com.example.Ecommerce_Project.exception;

public class CustomerException extends RuntimeException{
    public CustomerException() {
        super();
    }

    public CustomerException(String message) {
        super(message);
    }
}
