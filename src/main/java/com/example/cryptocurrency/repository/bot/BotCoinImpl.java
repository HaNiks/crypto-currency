package com.example.cryptocurrency.repository.bot;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotCoinImpl {

    void getAllCoin(StringBuilder output);
    void addNewCoin(Update update, StringBuilder output);
}
