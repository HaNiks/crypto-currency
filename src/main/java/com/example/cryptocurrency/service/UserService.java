package com.example.cryptocurrency.service;

import com.example.cryptocurrency.dto.UserDTO;
import com.example.cryptocurrency.model.Price;
import com.example.cryptocurrency.model.User;
import com.example.cryptocurrency.repository.PriceRepository;
import com.example.cryptocurrency.repository.UserRepository;
import org.modelmapper.ModelMapper;
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

    private final PriceRepository priceRepository;
    private final UserRepository userRepository;
    private final PriceService priceService;
    private final ModelMapper modelMapper;
    private User user;
    private final Logger log;

    public UserService(PriceRepository priceRepository, UserRepository userRepository, PriceService priceService, ModelMapper modelMapper) {
        this.priceRepository = priceRepository;
        this.userRepository = userRepository;
        this.priceService = priceService;
        this.modelMapper = modelMapper;
        this.log = LoggerFactory.getLogger(User.class);
    }

    @Scheduled(fixedRate = 60 * 1000)
    @Async
    public void update() {
        List<Price> prices = priceRepository.findAll();
        for (Price price : prices) {
            priceService.updatePrice(price.getSymbol());
            if (user != null) {
                checkLog(price);
            }
        }
    }

    public User notify(String userName, String symbol) {
        user = new User();
        Price price = priceService.findPriceBySymbol(symbol);
        priceService.updatePrice(symbol);
        user.setOldPrice(price.getPriceUsd());
        user.setUserName(userName);
        user.setSymbol(symbol);
        userRepository.save(user);
        return user;
    }

    @Async
    public void checkLog(Price price) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            double newPrice = price.getPriceUsd();
            double percent = (newPrice - user.getOldPrice()) / user.getOldPrice() * 100;
            String print = getMessage(newPrice, percent);
            if (Math.abs(percent) >= 1) {
                log.warn(print);
            } else log.info(print);
        }
    }

    @Async
    public String getMessage(double newPrice, double percent) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return LocalDateTime.now() + "ID: " + user.getId() + " Name: " +
                user.getUserName() + ", symbol: " + user.getSymbol() + ", old price: " +
                user.getOldPrice() + ", new price: " + newPrice +
                ", percent: " + decimalFormat.format(Math.abs(percent));
    }

    public List<User> deleteUser(String username) {
        List<User> deleteUser = userRepository.findAllByUserName(username);
        userRepository.deleteAll(deleteUser);
        return deleteUser;
    }

    public List<User> getUser(String userName) {
        return userRepository.findAllByUserName(userName);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
