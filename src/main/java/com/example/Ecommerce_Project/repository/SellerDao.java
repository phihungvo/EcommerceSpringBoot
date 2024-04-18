package com.example.Ecommerce_Project.repository;

import com.example.Ecommerce_Project.models.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerDao extends JpaRepository<Seller, Integer> {

    Optional<Seller> findByMobile(String mobile);
}
