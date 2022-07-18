package com.example.cryptocurrency.service;

import com.example.cryptocurrency.model.Price;
import com.example.cryptocurrency.model.User;
import com.example.cryptocurrency.repository.PriceRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final PriceRepo priceRepo;
    private final PriceService priceService;
    private final List<User> users;

    private User user;
    private final Logger log;

    public UserService(PriceRepo priceRepo, PriceService priceService) {
        this.priceRepo = priceRepo;
        this.priceService = priceService;
        this.log = LoggerFactory.getLogger(User.class);
        users = new ArrayList<>();
    }

    @Scheduled(fixedRate = 60 * 1000)
    @Async
    public void update() {
        List<Price> prices = priceRepo.findAll();
        for (Price price : prices) {
            priceService.updatePrice(price.getSymbol());
            if (user != null) {
                notifyUser(price.getSymbol());
            }
        }
    }

    public User setUserName(String userName, String symbol) {
        user = new User();
        user.setOldPrice(priceService.findPrice(symbol).getPriceUsd());
        user.setNewPrice(priceService.findPrice(symbol).getPriceUsd());
        user.setId(users.size() + 1);
        user.setUserName(userName);
        user.setSymbol(symbol);
        users.add(user);
        return user;
    }

    public void notifyUser(String symbol) {
        Price price = priceService.findPrice(symbol);
        if (user.getUserName() != null && price.getSymbol().equals(user.getSymbol())) {
            checkLog();
        }
    }

    private void checkLog() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        user.setNewPrice(priceService.findPrice(user.getSymbol()).getPriceUsd());
        double percent;
        for (User user : users) {
            percent = (user.getNewPrice() - user.getOldPrice()) / user.getOldPrice() * 100;
            if (Math.abs(percent) >= 1) {
                log.warn(LocalDateTime.now() + "ID: " + user.getId() + " Name: " +
                        user.getUserName() + ", symbol: " + user.getSymbol() + ", old price: " +
                        user.getOldPrice() + ", new price: " + user.getNewPrice() +
                        ", percent: " + decimalFormat.format(Math.abs(percent)));
            }
        }
    }
}
