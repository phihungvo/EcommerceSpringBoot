package com.example.Ecommerce_Project.service;

import com.example.Ecommerce_Project.models.CartDTO;
import com.example.Ecommerce_Project.models.CartItem;

public interface CartItemService {
    public CartItem createItemforCart(CartDTO cartDTO);
}
