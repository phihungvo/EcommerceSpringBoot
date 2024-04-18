package com.example.Ecommerce_Project.controller;

public class ProductNotFound extends RuntimeException{


    public ProductNotFound() {
    }

    public ProductNotFound(String message) {
        super(message);
    }
}
