package com.example.Ecommerce_Project.repository;

import com.example.Ecommerce_Project.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDao extends JpaRepository<Address, Integer> {
}
