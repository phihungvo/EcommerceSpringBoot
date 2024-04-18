package com.example.Ecommerce_Project.controller;

import com.example.Ecommerce_Project.models.Cart;
import com.example.Ecommerce_Project.models.CartDTO;
import com.example.Ecommerce_Project.repository.CartDao;
import com.example.Ecommerce_Project.repository.CustomerDao;
import com.example.Ecommerce_Project.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private CustomerDao customerDao;

    @PostMapping(value = "/cart/add")
    public ResponseEntity<Cart> addProductToCartHander(@RequestBody CartDTO cartDTO, @RequestHeader("token") String token){

        Cart cart = cartService.addProductToCart(cartDTO, token);
        return new ResponseEntity<Cart>(cart, HttpStatus.CREATED);
    }

    @GetMapping(value = "/cart")
    public ResponseEntity<Cart> getCartProductHandler(@RequestHeader("token")String token){
        return new ResponseEntity<>(cartService.getCartProduct(token), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "/cart")
    public ResponseEntity<Cart> removeProductFromCartHander(@RequestBody CartDTO cartDTO, @RequestHeader("token")String token){

        Cart cart = cartService.removeProductFromCart(cartDTO, token);
        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @DeleteMapping(value = "/cart/clear")
    public ResponseEntity<Cart> clearCartHandler(@RequestHeader("token")String token){
        return new ResponseEntity<>(cartService.clearCart(token), HttpStatus.ACCEPTED);
    }
}


































