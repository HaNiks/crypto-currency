package com.example.cryptocurrency.bot;

import com.example.cryptocurrency.service.BotService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class CurrencyBot extends TelegramLongPollingBot {

    private final BotService botService;

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    public CurrencyBot(BotService botService) {
        this.botService = botService;
    }


    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();
        StringBuilder output = new StringBuilder();

        String inputText = update.getMessage().getText();
        switch (inputText) {
            case "/price" -> botService.getAllCoin(output);
            case "/help" -> botService.getBotHelpInfo(output);
            case "/addCoin" -> botService.addNewCoin(update, output);
            case "/users" -> botService.getAllUsers(output);
        }
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(output.toString());

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }
}
