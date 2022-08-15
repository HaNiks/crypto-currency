package com.example.cryptocurrency.repository;

import com.example.cryptocurrency.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoinRepository extends JpaRepository<Coin, Integer> {
    Optional<Coin> findCoinBySymbol(String symbol);
}
