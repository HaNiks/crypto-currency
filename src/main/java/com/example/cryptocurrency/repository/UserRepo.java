package com.example.cryptocurrency.repository;

import com.example.cryptocurrency.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Integer> {
    List<User> findAllByUserName(String username);
    User findBySymbol(String symbol);
}
