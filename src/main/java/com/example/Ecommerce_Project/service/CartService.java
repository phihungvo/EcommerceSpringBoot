package com.example.Ecommerce_Project.service;

import com.example.Ecommerce_Project.exception.CartItemNotFound;
import com.example.Ecommerce_Project.exception.ProductNotFoundException;
import com.example.Ecommerce_Project.models.Cart;
import com.example.Ecommerce_Project.models.CartDTO;
import com.example.Ecommerce_Project.models.Customer;
import com.example.Ecommerce_Project.models.Product;

public interface CartService {

    public Cart addProductToCart(CartDTO cart, String token) throws CartItemNotFound;

    public Cart getCartProduct(String token);

    public Cart removeProductFromCart(CartDTO cartDto, String token) throws ProductNotFoundException;

    public Cart clearCart(String token);
}
