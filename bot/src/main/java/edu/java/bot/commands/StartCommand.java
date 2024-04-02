package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.clients.ScrapperClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {

    @Autowired
    ScrapperClient scrapperClient;

    private static final String NAME = "/start";
    private static final String DESCRIPTION = "начать работу с ботом";
    private static final String MESSAGE = "Привет! Я бот, который может оповещать тебя об обновленияx "
        + "отслеживаемых тобой статей и проектов на GitHub и Stack Overflow.";


    @Override
    public String handle(Update update) {
        Long chatId = update.message().chat().id();
        scrapperClient.addChat(chatId);
        return MESSAGE;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
