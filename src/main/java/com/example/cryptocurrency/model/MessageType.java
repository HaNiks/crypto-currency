package com.example.cryptocurrency.model;

import com.example.cryptocurrency.repository.BotCommand;
import com.example.cryptocurrency.service.bot_service.AllCoinCommand;
import com.example.cryptocurrency.service.bot_service.AllUsersCommand;
import com.example.cryptocurrency.service.bot_service.HelpCommand;
import com.example.cryptocurrency.service.bot_service.NewCoinCommand;

import java.lang.reflect.InvocationTargetException;

public enum MessageType {
    PRICE("price", AllCoinCommand.class), HELP("help", AllUsersCommand.class),
    NEW_COIN("addCoin", HelpCommand.class), USERS("/users", NewCoinCommand.class);

    private final String message;
    private Class<? extends BotCommand> botCommand;

    MessageType(String message, Class<? extends BotCommand> botCommand) {
        this.message = message;
        this.botCommand = botCommand;
    }

    public static MessageType getTemplateByCode(String message) {
        for (MessageType type : MessageType.values()) {
            if (type.message.equals(message)) {
                return type;
            }
        }
        return null;
    }

    public BotCommand getBotCommand() throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        return botCommand.getDeclaredConstructor().newInstance();
    }
}
