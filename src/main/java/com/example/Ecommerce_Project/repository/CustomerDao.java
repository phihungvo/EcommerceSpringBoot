package com.example.Ecommerce_Project.repository;

import com.example.Ecommerce_Project.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByMobileNo(String mobileNo);
    Optional<Customer> findByEmailId(String emailId);
    Optional<Customer> findByMobileNoOrEmailId(String mobileNo, String emailId);
}
