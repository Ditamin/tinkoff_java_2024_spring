package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.clients.ScrapperClient;
import edu.java.bot.model.ListLinksResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {
    @Autowired
    ScrapperClient scrapperClient;

    private static final String NAME = "/list";
    private static final String DESCRIPTION = "вывести список отслеживаемых ссылок";
    private static final String LIST_EMPTY = "Список пуст";
    private static final String MESSAGE = "Отслеживаемые ссылки:\n";

    @Override
    public String handle(Update update) {
        Long chatId = update.message().chat().id();
        ListLinksResponse response = scrapperClient.getLinks(chatId);

        if (response.size() == 0) {
            return LIST_EMPTY;
        }

        StringBuilder answer = new StringBuilder();

        for (var link : response.links()) {
            answer.append(link.url()).append("\n");
        }

        return MESSAGE + answer;
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
