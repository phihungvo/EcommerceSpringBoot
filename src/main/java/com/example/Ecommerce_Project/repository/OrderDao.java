package com.example.Ecommerce_Project.repository;

import com.example.Ecommerce_Project.models.Customer;
import com.example.Ecommerce_Project.models.Order;
import com.example.Ecommerce_Project.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {

    public List<Order> findByDate(LocalDate date);

//    @Query("SELECT c.orders FROM Customer c WHERE c.customerId = customerId")
//    public List<Order> getListOfOrdersByCustomerId(@Param("customerId") Integer customerId);

    @Query("SELECT c FROM Customer c WHERE c.customerId = customerId")
    public Customer getCustomerByOrderid(@Param("customerId")Integer customerId);

//    public List<Product> getListOfProductsByOrderId(Integer orderId);


//	@Query("update Order o set o.orderStatus = OrderStatusValues.CANCELLED WHERE o.OrderId=OrderId ")
//	public Order CancelOrderByOrderId(@Param("OrderId") Integer OrderId);

}





























