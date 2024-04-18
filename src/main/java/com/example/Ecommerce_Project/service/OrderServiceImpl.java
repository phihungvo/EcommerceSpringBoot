package com.example.Ecommerce_Project.service;

import com.example.Ecommerce_Project.exception.LoginException;
import com.example.Ecommerce_Project.exception.OrderException;
import com.example.Ecommerce_Project.models.*;
import com.example.Ecommerce_Project.repository.OrderDao;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CartServiceImpl cartServiceImpl;


    @Override
    public Order saveOrder(OrderDTO orderDTO, String token) throws LoginException, OrderException {

        Order newOrder = new Order();

        Customer loggedInCustomer = customerService.getLoggedInCustomerDetails(token);

        if(loggedInCustomer != null){
            newOrder.setCustomer(loggedInCustomer);
            String usersCardNumber = loggedInCustomer.getCreditCard().getCardNumber();
            String userGivenCardNumber = orderDTO.getCardNumber().getCardNumber();
            List<CartItem> productsInCart = loggedInCustomer.getCustomerCart().getCartItem();
            List<CartItem> productsInOrder = new ArrayList<>(productsInCart);

            newOrder.setOrdercartItem(productsInOrder);
            newOrder.setTotal(loggedInCustomer.getCustomerCart().getCartTotal());

            if(productsInCart.size() != 0){
                if((usersCardNumber.equals(userGivenCardNumber))
                && (orderDTO.getCardNumber().getCardValidity().equals(loggedInCustomer.getCreditCard().getCardValidity())
                && (orderDTO.getCardNumber().getCardCVV().equals(loggedInCustomer.getCreditCard().getCardCVV())))){

                    newOrder.setCardNumber(orderDTO.getCardNumber().getCardNumber());
                    newOrder.setAddress(loggedInCustomer.getAddress().get(orderDTO.getAddressType()));
                    newOrder.setDate(LocalDate.now());
                    newOrder.setOrderStatus(OrderStatusValues.SUCCESS);
                    System.out.println(usersCardNumber);
                    List<CartItem> cartItemsList= loggedInCustomer.getCustomerCart().getCartItem();

                    for(CartItem cartItem : cartItemsList ) {
                        Integer remainingQuantity = cartItem.getCartProduct().getQuantity()-cartItem.getCartItemQuantity();
                        if(remainingQuantity < 0 || cartItem.getCartProduct().getStatus() == ProductStatus.OUTOSTOCK) {
                            CartDTO cartdto = new CartDTO();
                            cartdto.setProductId(cartItem.getCartProduct().getProductId());
                            cartServiceImpl.removeProductFromCart(cartdto, token);
                            throw new OrderException("Product "+ cartItem.getCartProduct().getProductName() + " OUT OF STOCK");
                        }
                        cartItem.getCartProduct().setQuantity(remainingQuantity);
                        if(cartItem.getCartProduct().getQuantity()==0) {
                            cartItem.getCartProduct().setStatus(ProductStatus.OUTOSTOCK);
                        }
                    }
                    cartServiceImpl.clearCart(token);
                    //System.out.println(newOrder);
                    return orderDao.save(newOrder);
                }
                else {
                    System.out.println("Not same");
                    newOrder.setCardNumber(null);
                    newOrder.setAddress(loggedInCustomer.getAddress().get(orderDTO.getAddressType()));
                    newOrder.setDate(LocalDate.now());
                    newOrder.setOrderStatus(OrderStatusValues.PENDING);
                    cartServiceImpl.clearCart(token);
                    return orderDao.save(newOrder);

                }
            }
            else {
                throw new OrderException("No products in Cart");
            }

        }
        else {
            throw new LoginException("Invalid session token for customer"+"Kindly Login");
        }
    }

    @Override
    public Order getOrderByOrderId(Integer OrderId) throws OrderException {
        return orderDao.findById(OrderId).orElseThrow( () -> new OrderException("No order exists with given OrderId" +OrderId));
    }

    @Override
    public List<Order> getAllOrders() throws OrderException {

        List<Order> orders = orderDao.findAll();
        if(orders.size() > 0)
            return orders;
        else
            throw new OrderException("No orders exists on your account");
    }

    @Override
    public Order cancelOrderByOrderId(Integer OrderId, String token) throws OrderException {
        Order order = orderDao.findById(OrderId).orElseThrow(() -> new OrderException("No order exists with given orderid" +OrderId));
        if(order.getCustomer().getCustomerId() == customerService.getLoggedInCustomerDetails(token).getCustomerId()){
            if(order.getOrderStatus() == OrderStatusValues.PENDING){
                order.setOrderStatus(OrderStatusValues.CAnCELLED);
                orderDao.save(order);
                return order;
            }
            else if (order.getOrderStatus() == OrderStatusValues.SUCCESS){
                order.setOrderStatus(OrderStatusValues.CAnCELLED);
                List<CartItem> cartItemList = order.getOrdercartItem();

                for (CartItem cartItem : cartItemList){
                    Integer addedQuantity = cartItem.getCartProduct().getQuantity() + cartItem.getCartItemQuantity();
                    cartItem.getCartProduct().setQuantity(addedQuantity);
                    if(cartItem.getCartProduct().getStatus() == ProductStatus.OUTOSTOCK){
                        cartItem.getCartProduct().setStatus(ProductStatus.AVAILABLE);
                    }
                }
                orderDao.save(order);
                return order;
            }else{
                throw new OrderException("Order was already cancelled");
            }
        }
        else{
            throw new LoginException("Invalid session token for customer"+"Kindly Login");
        }
    }



    @Override
    public Order updateOrderByOrder(OrderDTO orderDTO, Integer OrderId, String token) throws OrderException, LoginException {


        Order existingOrder =orderDao.findById(OrderId).orElseThrow(() -> new OrderException("No order exists with OrderId "+OrderId));

        if(existingOrder.getCustomer().getCustomerId() == customerService.getLoggedInCustomerDetails(token).getCustomerId()){
            //existingOrder.setCardNumber(orderdto.getCardNumber().getCardNumber());
            //existingOrder.setAddress(existingOrder.getCustomer().getAddress().get(orderdto.getAddressType()));

            Customer loggedInCustomer = customerService.getLoggedInCustomerDetails(token);

            String usersCardNumber = loggedInCustomer.getCreditCard().getCardNumber();
            String userGivenCardNumber = orderDTO.getCardNumber().getCardNumber();

            if((usersCardNumber.equals(userGivenCardNumber))
                  && (orderDTO.getCardNumber().getCardValidity().equals(loggedInCustomer.getCreditCard().getCardValidity())
                      && (orderDTO.getCardNumber().getCardCVV().equals(loggedInCustomer.getCreditCard().getCardCVV())))){

                existingOrder.setCardNumber(orderDTO.getCardNumber().getCardNumber());
                existingOrder.setAddress(existingOrder.getCustomer().getAddress().get(orderDTO.getAddressType()));
                existingOrder.setOrderStatus(OrderStatusValues.SUCCESS);

                List<CartItem> cartItemsList = existingOrder.getOrdercartItem();
                for(CartItem cartItem : cartItemsList){
                    Integer remainingQuantity = cartItem.getCartProduct().getQuantity() - cartItem.getCartItemQuantity();

                    if(remainingQuantity < 0 || cartItem.getCartProduct().getStatus() == ProductStatus.OUTOSTOCK){
                        CartDTO cartDTO = new CartDTO();
                        cartDTO.setProductId(cartItem.getCartProduct().getProductId());
                        cartServiceImpl.removeProductFromCart(cartDTO, token);
                    }

                    cartItem.getCartProduct().setQuantity(remainingQuantity);
                    if(cartItem.getCartProduct().getQuantity() == 0){
                        cartItem.getCartProduct().setStatus(ProductStatus.OUTOSTOCK);
                    }
                }
                return orderDao.save(existingOrder);
            }
            else{
                throw new OrderException("Incorrect Card Number Again" + usersCardNumber + userGivenCardNumber);
            }
        }else{
            throw new LoginException("Invalid session token for customer"+ "Kindly Login");
        }
    }


    @Override
    public List<Order> getAllOrdersByDate(LocalDate date) throws OrderException {
        List<Order> listOfOrdersOnTheDay = orderDao.findByDate(date);
        return listOfOrdersOnTheDay;
    }

    @Override
    public Customer getCustomerByOrderId(Integer orderId) throws OrderException {

        Optional<Order> opt = orderDao.findById(orderId);
        if(opt.isPresent()){
            Order existingOrder = opt.get();
            return orderDao.getCustomerByOrderid(existingOrder.getCustomer().getCustomerId());
        }else{
            throw new OrderException("No order exists with orderId" +orderId);
        }
    }
}




























