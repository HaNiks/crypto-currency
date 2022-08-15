package com.example.cryptocurrency.service.bot_service;

import com.example.cryptocurrency.repository.BotCommand;
import com.example.cryptocurrency.service.CoinService;
import com.example.cryptocurrency.service.PriceService;
import com.example.cryptocurrency.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class AllCoinCommand implements BotCommand {

    @Override
    public void sendCommand(StringBuilder stringBuilder, PriceService priceService,
                            CoinService coinService, UserService userService) {
        stringBuilder.append("The actual price of the CRYPTOCURRENCY:\n \n");
        priceService.findAll()
                .forEach(price -> {
                    String name = coinService.findCoinBySymbol(price.getSymbol()).getName();
                    stringBuilder.append(name)
                            .append(" (").append(price.getSymbol())
                            .append("), price: ").append(price.getPriceUsd())
                            .append("$ \n");
                });
        stringBuilder.append("\n Get help: /help");
    }
}
