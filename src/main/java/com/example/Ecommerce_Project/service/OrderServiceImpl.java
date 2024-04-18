package com.example.Ecommerce_Project.service;

import com.example.Ecommerce_Project.exception.LoginException;
import com.example.Ecommerce_Project.exception.OrderException;
import com.example.Ecommerce_Project.models.*;
import com.example.Ecommerce_Project.repository.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        return null;
    }

    @Override
    public List<Order> getAllOrders() throws OrderException {
        return null;
    }

    @Override
    public Order cancelOrderByOrderId(Integer OrderId, String token) throws OrderException {
        return null;
    }

    @Override
    public Order updateOrderByOrder(OrderDTO order, Integer OrderId, String token) throws OrderException, LoginException {
        return null;
    }

    @Override
    public List<Order> getAllOrdersByDate(LocalDate date) throws OrderException {
        return null;
    }

    @Override
    public Customer getCustomerByOrderId(Integer orderId) throws OrderException {
        return null;
    }
}
