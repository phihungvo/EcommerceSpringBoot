package com.example.Ecommerce_Project.controller;

import com.example.Ecommerce_Project.models.Customer;
import com.example.Ecommerce_Project.models.Order;
import com.example.Ecommerce_Project.models.OrderDTO;
import com.example.Ecommerce_Project.repository.OrderDao;
import com.example.Ecommerce_Project.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderService orderService;


    @PostMapping("/order/place")
    public ResponseEntity<Order> addTheNewOrder(@Valid @RequestBody OrderDTO orderDTO, @RequestHeader("token")String token){

        Order savedOrder = orderService.saveOrder(orderDTO, token);
        return new ResponseEntity<Order>(savedOrder, HttpStatus.CREATED);
    }


    @GetMapping("/orders")
    public List<Order> getAllOrders(){

        List<Order> listOfAllOrders = orderService.getAllOrders();
        return listOfAllOrders;
    }


    @GetMapping("/orders/{orderId}")
    public Order getOrdersByOrderId(@PathVariable("orderId") Integer orderId){
        return orderService.getOrderByOrderId(orderId);
    }


    @DeleteMapping("/orders/{orderId}")
    public Order cancelTheOrderByOrderId(@PathVariable("orderId")Integer orderId, @RequestHeader("token")String token){
        return orderService.cancelOrderByOrderId(orderId, token);
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<Order> updateOrderByOrder(@Valid @RequestBody OrderDTO orderDTO, @PathVariable("orderId")Integer orderId, @RequestHeader("token")String token){

        Order updatedOrder = orderService.updateOrderByOrder(orderDTO, orderId, token);

        return new ResponseEntity<Order>(updatedOrder, HttpStatus.ACCEPTED);
    }

    @GetMapping("/orders/by/date")
    public List<Order> getOrderByDate(@RequestParam("date")String date){
        DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate ld = LocalDate.parse(date, dtf);
        return orderService.getAllOrdersByDate(ld);
    }


    @GetMapping("/customer/{orderId}")
    public Customer getCusomerDetailsByOrderId(@PathVariable("orderId") Integer orderId){
        return orderService.getCustomerByOrderId(orderId);
    }

}




























