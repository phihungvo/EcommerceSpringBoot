package com.example.Ecommerce_Project.repository;

import com.example.Ecommerce_Project.models.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionDao extends JpaRepository<UserSession, Integer> {

    Optional<UserSession> findByToken(String token);
    Optional<UserSession> findByUserId(Integer userId);
}
