package com.example.cryptocurrency.service.bot_service;

import com.example.cryptocurrency.repository.BotCommand;
import com.example.cryptocurrency.service.CoinService;
import com.example.cryptocurrency.service.PriceService;
import com.example.cryptocurrency.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class HelpCommand implements BotCommand {

    @Override
    public void sendCommand(StringBuilder stringBuilder, PriceService priceService, CoinService coinService, UserService userService) {
        stringBuilder.append("I can help with tracking cryptocurrency prices. \n \n")
                .append("You can control me by sending these commands: \n \n")
                .append("Coin command: \n")
                .append("/price - actual coin price \n")
                .append("/add - add a new cryptocurrency\n \n")
                .append("User command \n")
                .append("/users - shows all registered users");
    }
}
