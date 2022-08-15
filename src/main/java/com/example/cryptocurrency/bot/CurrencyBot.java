package com.example.cryptocurrency.bot;

import com.example.cryptocurrency.model.MessageType;
import com.example.cryptocurrency.service.CoinService;
import com.example.cryptocurrency.service.PriceService;
import com.example.cryptocurrency.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;


@Component
public class CurrencyBot extends TelegramLongPollingBot {

    private final PriceService priceService;
    private final CoinService coinService;
    private final UserService userService;

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    public CurrencyBot(PriceService priceService, CoinService coinService, UserService userService) {
        this.priceService = priceService;
        this.coinService = coinService;
        this.userService = userService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();
        StringBuilder output = new StringBuilder();

        String inputText = update.getMessage().getText();
        try {
            Objects.requireNonNull(MessageType.getTemplateByCode(inputText))
                    .getBotCommand()
                    .sendCommand(output, priceService, coinService, userService);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
