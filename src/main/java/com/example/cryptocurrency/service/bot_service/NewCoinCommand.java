package com.example.cryptocurrency.service.bot_service;

import com.example.cryptocurrency.model.Coin;
import com.example.cryptocurrency.model.Price;
import com.example.cryptocurrency.repository.BotCommand;
import com.example.cryptocurrency.service.CoinService;
import com.example.cryptocurrency.service.PriceService;
import com.example.cryptocurrency.service.UserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class NewCoinCommand implements BotCommand {

    @Override
    public void sendCommand(StringBuilder stringBuilder, PriceService priceService, CoinService coinService, UserService userService) {
//        @Override
//        public void sendNewCoin(Update update, StringBuilder output) {
//            int id = 55;
//            Coin coin = coinService.saveNewCoin(id);
//            Price price = priceService.findPriceBySymbol(coin.getSymbol());
//            output.append("A new cryptocurrency has been added: \n")
//                    .append(coin.getName())
//                    .append(" (").append(price.getSymbol())
//                    .append("), price: ").append(price.getPriceUsd())
//                    .append("$ \n");
//        }
    }
}
