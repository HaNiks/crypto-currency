package com.example.cryptocurrency.repository;

import com.example.cryptocurrency.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepo extends JpaRepository<Coin, Integer> {
    Coin findCoinBySymbol(String symbol);
}
