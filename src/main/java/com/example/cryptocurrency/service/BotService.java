package com.example.cryptocurrency.service;

import org.springframework.stereotype.Service;

@Service
public record BotService(UserService userService) {

    public void printBotHelpInfo(StringBuilder output) {
        output.append("I can help with tracking cryptocurrency prices. \n \n")
                .append("You can control me by sending these commands: \n \n")
                .append("Coin command: \n")
                .append("/price - actual coin price \n")
                .append("/addCoin - add a new cryptocurrency\n \n")
                .append("User command \n")
                .append("/users - shows all registered users");
    }
}
