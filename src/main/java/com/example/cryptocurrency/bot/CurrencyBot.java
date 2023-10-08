package com.example.cryptocurrency.bot;

import com.example.cryptocurrency.service.BotService;
import com.example.cryptocurrency.service.CoinService;
import com.example.cryptocurrency.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
@RequiredArgsConstructor
public class CurrencyBot extends TelegramLongPollingBot {

    private final BotService botService;
    private final CoinService coinService;
    private final UserService userService;

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;


    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();
        StringBuilder output = new StringBuilder();

        String inputText = update.getMessage().getText();
        switch (inputText) {
            case "/price" -> coinService.printAllCoin(output);
            case "/help" -> botService.printBotHelpInfo(output);
            case "/addCoin" -> coinService.addCoin(update, output);
            case "/users" -> userService.printAllUsers(output);
            default -> output.append("Command not ssfound \nGet help: /help");
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
