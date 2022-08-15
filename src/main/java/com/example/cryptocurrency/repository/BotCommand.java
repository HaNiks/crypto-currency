package com.example.cryptocurrency.repository;

import com.example.cryptocurrency.service.CoinService;
import com.example.cryptocurrency.service.PriceService;
import com.example.cryptocurrency.service.UserService;

public interface BotCommand {

    void sendCommand(StringBuilder stringBuilder, PriceService priceService,
                     CoinService coinService, UserService userService);
}
