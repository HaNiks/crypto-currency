package com.example.cryptocurrency.service;

import org.springframework.stereotype.Service;

@Service
public record BotService(CoinService coinService, UserService userService, PriceService priceService) {

    public void getAllPriceToBot(StringBuilder output) {
        output.append("The actual price of the CRYPTOCURRENCY:\n \n");
        priceService.getAllPrice()
                .forEach(price -> {
                    String name = coinService.findCoinBySymbol(price.getSymbol()).getName();
                    output.append("Name: ").append(name)
                            .append(", symbol: ").append(price.getSymbol())
                            .append(", price: ").append(price.getPriceUsd())
                            .append("$ \n");
                });
        output.append("\n Get help: /help");
    }

    public void getBotHelpInfo(StringBuilder output) {
        output.append("I can help with tracking cryptocurrency prices. \n \n")
                .append("You can control me by sending these commands: \n \n")
                .append("Price command: \n")
                .append("/price - actual coin price");
    }


}
