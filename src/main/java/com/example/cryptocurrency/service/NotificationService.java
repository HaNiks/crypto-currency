package com.example.cryptocurrency.service;

import com.example.cryptocurrency.dao.CoinRepo;
import com.example.cryptocurrency.model.Coin;
import com.example.cryptocurrency.model.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final RestTemplate restTemplate;
    private final CoinService coinService;
    private final CoinRepo coinRepo;
    private final Logger log;
    private final Notification notification;


    public NotificationService(CoinService coinService, CoinRepo coinRepo) {
        this.coinService = coinService;
        this.coinRepo = coinRepo;
        this.log = LoggerFactory.getLogger(CoinRepo.class);
        this.restTemplate = new RestTemplate();
        notification = new Notification();
    }

    @Scheduled(fixedRate = 10 * 1000)
    @Async
    public void update() {
        List<Coin> coins = coinService.findAll();
        for (Coin coin : coins) {
            this.updatePrice(coin.getId(), this.getNewPrice(coin.getSymbol()));
            notifyUser(coin.getSymbol());
        }
    }

    public void updatePrice(int id, double price) {
        Coin coin = coinService.findById(id);
        coin.setPrice(price);
        coinRepo.save(coin);
    }

    public void setUsername(String username, String symbol) {
        notification.setOldPrice(getCoinBySymbol(symbol).getPrice());
        notification.setId(getCoinBySymbol(symbol).getId());
        notification.setUsername(username);
        notification.setSymbol(symbol);
        notifyUser(symbol);
    }

    public void notifyUser(String symbol) {
        for (Coin coin : coinService.findAll()) {
            if (coinService.findById(coin.getId()).getSymbol().equals(symbol)) {
                notification.setNewPrice(coin.getPrice());
                if (notification.getUsername() != null && coin.getSymbol().equals(notification.getSymbol())) {
                    checkLog();
                    break;
                }
            }
        }

    }

    private double getNewPrice(String symbol) {
        String url = "https://api.coinlore.net/api/ticker/?id=" + getCoinBySymbol(symbol).getId();
        Coin[] coins = restTemplate.getForObject(url, Coin[].class);
        return coins[0].getPrice();
    }

    private Coin getCoinBySymbol(String symbol) {
        return coinRepo.findAll().stream()
                .filter(c -> c.getSymbol().equals(symbol))
                .findFirst().get();
    }

    private void checkLog() {
        double percent = (notification.getNewPrice() - notification.getOldPrice()) * 100 / notification.getOldPrice();
        if (Math.abs(percent) >= 1) {
            notification.setLog(new StringBuilder(LocalDateTime.now() + "ID: " + notification.getId() + " Name: " +
                    notification.getUsername() + ", symbol: " + notification.getSymbol() + ", old price: " +
                    notification.getOldPrice() + ", new price: " + notification.getNewPrice() + ", percent: " + percent));
            log.warn(String.valueOf(notification.getLog()));
        }
    }
}
