package com.example.cryptocurrency.repository;

import com.example.cryptocurrency.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUserName(String userName);
}
