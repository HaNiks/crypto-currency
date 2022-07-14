package com.example.cryptocurrency.service;

import com.example.cryptocurrency.dao.CoinRepo;
import com.example.cryptocurrency.model.Coin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {

    private final RestTemplate restTemplate;
    private final CoinService coinService;
    private final CoinRepo coinRepo;
    private final Logger log;

    public NotificationService(CoinService coinService, CoinRepo coinRepo) {
        this.coinService = coinService;
        this.coinRepo = coinRepo;
        this.log = LoggerFactory.getLogger(CoinRepo.class);
        this.restTemplate = new RestTemplate();
    }

    @Scheduled(fixedRate = 60 * 1000)
    @Async
    public void update() {
        List<Coin> coins = coinService.findAll();
        for (Coin coin : coins) {
            System.out.println(coin.getName());
            this.updatePrice(coin.getId(), this.getNewPrice(coin.getSymbol()));
        }
    }

    public void updatePrice(int id, double price) {
        Coin coin = coinService.findById(id);
        coin.setPrice(price);
        System.out.println(price);
        coinRepo.save(coin);
    }

    public void notifyUser(String username, String symbol) {
        StringBuilder output = new StringBuilder("Crypto: " + username + ", symbol: " + symbol + ", percent: ");
        double oldPrice = 0;
        double newPrice = 0;
        for (Coin coin : coinRepo.findAll()) {
            if (coinService.findById(coin.getId()).getSymbol().equals(symbol)) {
                newPrice = getNewPrice(coin.getSymbol());
                oldPrice = coin.getPrice();
                output.append(oldPrice);
                break;
            }
        }
        double result = (newPrice - oldPrice) * 100 / oldPrice;
        if (Math.abs(result) >= 1)
            log.warn(String.valueOf(output) + result);
    }

    private double getNewPrice(String symbol) {
        String url = "https://api.coinlore.net/api/ticker/?id=" + getCoinBySymbol(symbol).getId();
        Coin[] coins = restTemplate.getForObject(url, Coin[].class);
        System.out.println(coins[0].getPrice());
        return coins[0].getPrice();
    }

    private Coin getCoinBySymbol(String symbol) {
        return coinRepo.findAll().stream()
                .filter(c -> c.getSymbol().equals(symbol))
                .findFirst().get();
    }
}
