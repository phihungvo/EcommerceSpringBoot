package com.example.Ecommerce_Project.service;

import com.example.Ecommerce_Project.exception.CustomerNotFoundException;
import com.example.Ecommerce_Project.exception.LoginException;
import com.example.Ecommerce_Project.exception.OrderException;
import com.example.Ecommerce_Project.models.Customer;
import com.example.Ecommerce_Project.models.Order;
import com.example.Ecommerce_Project.models.OrderDTO;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    public Order saveOrder(OrderDTO orderDTO, String token) throws LoginException, OrderException;
    public Order getOrderByOrderId(Integer OrderId) throws OrderException;
    public List<Order> getAllOrders() throws OrderException;
    public Order cancelOrderByOrderId(Integer OrderId, String token) throws OrderException;
    public Order updateOrderByOrder(OrderDTO order, Integer OrderId, String token) throws OrderException, LoginException;
    public List<Order> getAllOrdersByDate(LocalDate date) throws OrderException;
    public Customer getCustomerByOrderId(Integer orderId) throws OrderException;

//    public Customer getCustomerIdByToken(String token) throws CustomerNotFoundException;




}






























