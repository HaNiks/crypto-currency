package com.example.cryptocurrency.service;

import com.example.cryptocurrency.dao.CoinRepo;
import com.example.cryptocurrency.model.Coin;
import com.example.cryptocurrency.util.CoinNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
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

    public Coin findById(int id) {
        return coinRepo.findById(id).orElseThrow(CoinNotFoundException::new);
    }

    @PostConstruct
    public void save() {

    }

    @Scheduled(fixedRate = 60000)
    public void update() {

    }
}
