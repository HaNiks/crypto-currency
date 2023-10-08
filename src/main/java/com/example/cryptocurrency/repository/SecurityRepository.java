package com.example.cryptocurrency.repository;

import com.example.cryptocurrency.model.Security;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecurityRepository extends JpaRepository<Security, Long> {

    Optional<Security> findByName(String name);
}
