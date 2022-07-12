package com.example.cryptocurrency.dao;

import com.example.cryptocurrency.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepo extends JpaRepository<Coin, Integer> {

}
