package com.example.cryptocurrency.service;

import com.example.cryptocurrency.model.Price;
import com.example.cryptocurrency.model.User;
import com.example.cryptocurrency.repository.PriceRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final PriceRepo priceRepo;
    private final PriceService priceService;
    private User user;
    private final List<User> users;
    private final Logger log;

    public UserService(PriceRepo priceRepo, PriceService priceService) {
        this.priceRepo = priceRepo;
        this.priceService = priceService;
        this.log = LoggerFactory.getLogger(User.class);
        users = new ArrayList<>();
    }

    @Scheduled(fixedRate = 10 * 1000)
    @Async
    public void update() {
        List<Price> prices = priceRepo.findAll();
        for (Price price : prices) {
            priceService.updatePrice(price.getSymbol());
            if (user != null) {
                user.setNewPrice(price.getPrice());
                notifyUser(price.getSymbol());
            }
            System.out.println(price);
        }
    }

    public void setUserName(String userName, String symbol) {
        user = new User();
        user.setOldPrice(priceService.findPrice(symbol).getPrice());
        user.setId(priceService.findPrice(symbol).getId());
        user.setUserName(userName);
        user.setSymbol(symbol);
        users.add(user);
        System.out.println(users);
    }

    public void notifyUser(String symbol) {
        if (user.getUserName() != null && priceRepo.getPriceBySymbol(symbol).getSymbol().equals(user.getSymbol())) {
            checkLog();
        }
    }

    private void checkLog() {
        for (User user : users) {
            double percent = (user.getNewPrice() - user.getOldPrice()) / user.getOldPrice() * 100;
            if (Math.abs(percent) >= 0) {
                log.warn(LocalDateTime.now() + "ID: " + user.getId() + " Name: " +
                        user.getUserName() + ", symbol: " + user.getSymbol() + ", old price: " +
                        user.getOldPrice() + ", new price: " + user.getNewPrice() + ", percent: " + percent);
            }
        }
    }
}
