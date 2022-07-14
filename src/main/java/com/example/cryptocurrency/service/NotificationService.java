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

    @Scheduled(fixedRate = 60 * 1000)
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
        System.out.println(coin.getName());
        System.out.println(coin.getPrice());
        coinRepo.save(coin);
    }

    public void setUsername(String username, String symbol) {
        Coin coin = getCoinBySymbol(symbol);
        notification.setOldPrice(coin.getPrice());
        notification.setUsername(username);
        notifyUser(symbol);
    }

    public void notifyUser(String symbol) {
        for (Coin coin : coinRepo.findAll()) {
            if (coinService.findById(coin.getId()).getSymbol().equals(symbol)) {
                notification.setNewPrice(coin.getPrice());
                System.out.println(notification.getOldPrice());
                System.out.println(notification.getNewPrice());
                break;
            }
        }
        if (notification.getUsername() != null) {
            writeLog(symbol);
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

    private void writeLog(String symbol) {
        double percent = (notification.getNewPrice() - notification.getOldPrice()) * 100 / notification.getOldPrice();
        for (Coin coin : coinService.findAll()) {
            if (Math.abs(percent) >= 0.1 && coin.getSymbol().equals(symbol)) {
                notification.setLog(new StringBuilder("Name: " + notification.getUsername() + ", symbol: " + symbol + ", old price: " +
                        notification.getOldPrice() + ", new price: " + notification.getNewPrice() + ", percent: " + percent));
                log.warn(String.valueOf(notification.getLog()));
            }
        }
    }
}
