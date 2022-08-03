package com.example.cryptocurrency.service;

import com.example.cryptocurrency.model.Price;
import com.example.cryptocurrency.model.User;
import com.example.cryptocurrency.repository.PriceRepo;
import com.example.cryptocurrency.repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableScheduling
public class UserService {

    private final PriceRepo priceRepo;
    private final UserRepo userRepo;
    private final PriceService priceService;
    private User user;
    private final Logger log;

    public UserService(PriceRepo priceRepo, UserRepo userRepo, PriceService priceService) {
        this.priceRepo = priceRepo;
        this.userRepo = userRepo;
        this.priceService = priceService;
        this.log = LoggerFactory.getLogger(User.class);
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
        Price price = priceService.findPrice(symbol);
        priceService.updatePrice(symbol);
        user.setOldPrice(price.getPriceUsd());
        user.setUserName(userName);
        user.setSymbol(symbol);
        userRepo.save(user);
        return user;
    }

    @Async
    public void notifyUser(String symbol) {
        Price price = priceService.findPrice(symbol);
        if (price.getSymbol().equals(user.getSymbol())) {
            checkLog();
        }
    }

    public List<User> getUser(String userName) {
        return userRepo.findAllByUserName(userName);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Async
    public void checkLog() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        List<User> users = userRepo.findAll();
        for (User user : users) {
            double newPrice = priceService.findPrice(user.getSymbol()).getPriceUsd();
            double percent = (newPrice - user.getOldPrice()) / user.getOldPrice() * 100;
            String print = LocalDateTime.now() + "ID: " + user.getId() + " Name: " +
                    user.getUserName() + ", symbol: " + user.getSymbol() + ", old price: " +
                    user.getOldPrice() + ", new price: " + newPrice +
                    ", percent: " + decimalFormat.format(Math.abs(percent));
            if (Math.abs(percent) >= 1) {
                log.warn(print);
            } else log.info(print);
        }
    }

    public List<User> deleteUser(String username) {
        List<User> deleteUser = userRepo.findAllByUserName(username);
        userRepo.deleteAll(deleteUser);
        return deleteUser;
    }
}
