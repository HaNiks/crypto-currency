package com.example.cryptocurrency.service;

import com.example.cryptocurrency.model.Coin;
import com.example.cryptocurrency.model.Price;
import com.example.cryptocurrency.model.User;
import com.example.cryptocurrency.repository.bot.BotHelpImpl;
import com.example.cryptocurrency.repository.bot.BotCoinImpl;
import com.example.cryptocurrency.repository.bot.BotUserImpl;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Random;

@Service
public record BotService(CoinService coinService, UserService userService,
                         PriceService priceService) implements BotCoinImpl, BotHelpImpl, BotUserImpl {

    @Override
    public void getAllCoin(StringBuilder output) {
        output.append("The actual price of the CRYPTOCURRENCY:\n \n");
        priceService.findAll()
                .forEach(price -> {
                    String name = coinService.findCoinBySymbol(price.getSymbol()).getName();
                    output.append(name)
                            .append(" (").append(price.getSymbol())
                            .append("), price: ").append(price.getPriceUsd())
                            .append("$ \n");
                });
        output.append("\n Get help: /help");
    }

    @Override
    public void getBotHelpInfo(StringBuilder output) {
        output.append("I can help with tracking cryptocurrency prices. \n \n")
                .append("You can control me by sending these commands: \n \n")
                .append("Coin command: \n")
                .append("/price - actual coin price \n")
                .append("/addCoin - add a new cryptocurrency\n \n")
                .append("User command \n")
                .append("/users - shows all registered users");
    }

    @Override
    public void addNewCoin(Update update, StringBuilder output) {
        int id = (int) (Math.random() * 100);
        Coin coin = coinService.saveNewCoin(id);
        Price price = priceService.findPrice(coin.getSymbol());
        output.append("A new cryptocurrency has been added: \n")
                .append(coin.getName())
                .append(" (").append(price.getSymbol())
                .append("), price: ").append(price.getPriceUsd())
                .append("$ \n");
    }

    @Override
    public void getAllUsers(StringBuilder output) {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            output.append("No registered users");
        } else {
            output.append("All users:\n");
            users.forEach(user -> output.append(user.getUserName())
                    .append(" - symbol of the saved cryptocurrency: ").append(user.getSymbol())
                    .append(", start price: ").append(user.getOldPrice())
                    .append("$ \n"));
        }
        output.append("\nGet help: /help");
    }
}
