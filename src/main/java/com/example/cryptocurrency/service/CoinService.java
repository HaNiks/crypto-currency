package com.example.cryptocurrency.service;

import com.example.cryptocurrency.repository.CoinRepo;
import com.example.cryptocurrency.model.Coin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@EnableScheduling
public class CoinService {
    private final CoinRepo coinRepo;


    @Autowired
    public CoinService(CoinRepo coinRepo) {
        this.coinRepo = coinRepo;
    }

    public List<Coin> findAll() {
        return coinRepo.findAll();
    }

}
