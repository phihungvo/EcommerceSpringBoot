package com.example.Ecommerce_Project.service;

import com.example.Ecommerce_Project.exception.CartItemNotFound;
import com.example.Ecommerce_Project.exception.CustomerNotFoundException;
import com.example.Ecommerce_Project.exception.LoginException;
import com.example.Ecommerce_Project.exception.ProductNotFoundException;
import com.example.Ecommerce_Project.models.*;
import com.example.Ecommerce_Project.repository.CartDao;
import com.example.Ecommerce_Project.repository.CustomerDao;
import com.example.Ecommerce_Project.repository.ProductDao;
import com.example.Ecommerce_Project.repository.SessionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartDao cartDao;

    @Autowired
    private SessionDao sessionDao;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private LoginLogoutService loginService;

    @Autowired
    private ProductDao productDao;


    @Override
    public Cart addProductToCart(CartDTO cartDto, String token) throws CartItemNotFound {


        if(token.contains("customer") == false){
            throw new LoginException("Invalid session token for customer");
        }

        loginService.checkTokenStatus(token);

        UserSession user = sessionDao.findByToken(token).get();

        Optional<Customer> opt = customerDao.findById(user.getUserId());

        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");

        Customer existingCustomer = opt.get();

        Cart customerCart = existingCustomer.getCustomerCart();

        List<CartItem> cartItems = customerCart.getCartItem();

        CartItem item = cartItemService.createItemforCart(cartDto);

        if(cartItems.size() == 0){
            cartItems.add(item);
            customerCart.setCartTotal(item.getCartProduct().getPrice());
        }else{
            boolean flag = false;
            for(CartItem c : cartItems){
                if(c.getCartProduct().getProductId() == cartDto.getProductId()){
                    c.setCartItemQuantity(c.getCartItemQuantity() + 1);
                    customerCart.setCartTotal(customerCart.getCartTotal() + c.getCartProduct().getPrice());
                    flag = true;
                }
            }
            if(!flag){
                cartItems.add(item);
                customerCart.setCartTotal(customerCart.getCartTotal() + item.getCartProduct().getPrice());
            }
        }
        return cartDao.save(existingCustomer.getCustomerCart());
    }

    @Override
    public Cart getCartProduct(String token) {

        System.out.println("Inside get cart");

        if(token.contains("customer") == false){
            throw new LoginException("Invalid session token for customer");
        }

        loginService.checkTokenStatus(token);

        UserSession user = sessionDao.findByToken(token).get();

        Optional<Customer> opt = customerDao.findById(user.getUserId());

        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exists");

        Customer existingCustomer = opt.get();

//      System.out.println(existingCustomer);
//
//		System.out.println(existingCustomer.getCustomerCart());
//
//		System.out.println("Here reached");

        Integer cardId = existingCustomer.getCustomerCart().getCardId();

        Optional<Cart> optCart = cartDao.findById(cardId);

        if(optCart.isEmpty()){
            throw new CartItemNotFound("Cart not found by Id");
        }

        return optCart.get();
    }

    @Override
    public Cart removeProductFromCart(CartDTO cartDto, String token) throws ProductNotFoundException {
        if(token.contains("customer") == false){
            throw new LoginException("Invalid session token for customer");
        }

        loginService.checkTokenStatus(token);

        UserSession user = sessionDao.findByToken(token).get();

        Optional<Customer> opt = customerDao.findById(user.getUserId());

        if(opt.isEmpty()){
            throw new CustomerNotFoundException("Customer does not exist");
        }

        Customer existingCustomer = opt.get();

        Cart customerCart = existingCustomer.getCustomerCart();

        List<CartItem> cartItems = customerCart.getCartItem();

        if(cartItems.size() == 0){
            throw new CartItemNotFound("Cart is empty");
        }

        boolean flag = false;

        for(CartItem c : cartItems){
            System.out.println("Item "+c.getCartProduct());
            if(c.getCartProduct().getProductId() == cartDto.getProductId()){
                c.setCartItemQuantity(c.getCartItemQuantity() - 1);

                customerCart.setCartTotal(customerCart.getCartTotal() - c.getCartProduct().getPrice());

                if(c.getCartItemQuantity() == 0){
                    cartItems.remove(c);

                    return cartDao.save(customerCart);
                }
                flag = true;
            }
        }

        if(cartItems.size() == 0){
            cartDao.save(customerCart);
            throw new CartItemNotFound("Cart is empty now");
        }
        return cartDao.save(customerCart);
    }


    @Override
    public Cart clearCart(String token) {

        if(token.contains("customer") == false){
            throw new LoginException("Invalid session token for customer");
        }

        loginService.checkTokenStatus(token);

        UserSession user = sessionDao.findByToken(token).get();

        Optional<Customer> opt = customerDao.findById(user.getUserId());

        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exists");

        Customer existingCustomer = opt.get();

        Cart customerCart = existingCustomer.getCustomerCart();

        if(customerCart.getCartItem().size() == 0){
            throw new CartItemNotFound("Cart already empty");
        }

        List<CartItem> emptyCart = new ArrayList<>();

        customerCart.setCartItem(emptyCart);

        customerCart.setCartTotal(0.0);

        return cartDao.save(customerCart);
    }
}
































































