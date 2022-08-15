package com.example.cryptocurrency.service.bot_service;

import com.example.cryptocurrency.model.User;
import com.example.cryptocurrency.repository.BotCommand;
import com.example.cryptocurrency.service.CoinService;
import com.example.cryptocurrency.service.PriceService;
import com.example.cryptocurrency.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllUsersCommand implements BotCommand {

    @Override
    public void sendCommand(StringBuilder stringBuilder, PriceService priceService,
                            CoinService coinService, UserService userService) {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            stringBuilder.append("No registered users");
        } else {
            stringBuilder.append("All users:\n");
            users.forEach(user -> stringBuilder.append(user.getUserName())
                    .append(" - symbol of the saved cryptocurrency: ").append(user.getSymbol())
                    .append(", start price: ").append(user.getOldPrice())
                    .append("$ \n"));
        }
        stringBuilder.append("\nGet help: /help");
    }
}
