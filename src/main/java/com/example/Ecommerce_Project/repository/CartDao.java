package com.example.Ecommerce_Project.repository;

import com.example.Ecommerce_Project.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDao extends JpaRepository<Cart, Integer> {

//    public Map<Product, Integer> findByName(String productName);
//    public Cart findbyId(Integer cardId);
}
