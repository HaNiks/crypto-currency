package com.example.cryptocurrency.repository;

import com.example.cryptocurrency.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PriceRepo extends JpaRepository<Price, Integer> {
    Optional<Price> getPriceBySymbol(String symbol);
}
