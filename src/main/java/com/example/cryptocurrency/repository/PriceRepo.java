package com.example.cryptocurrency.repository;

import com.example.cryptocurrency.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepo extends JpaRepository<Price, Integer> {
    Price getPriceBySymbol(String symbol);
}
