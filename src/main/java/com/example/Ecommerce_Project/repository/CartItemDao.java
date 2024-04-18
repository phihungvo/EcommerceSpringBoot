package com.example.Ecommerce_Project.repository;

import com.example.Ecommerce_Project.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemDao extends JpaRepository<CartItem, Integer> {
}
