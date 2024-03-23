package edu.java.bot.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.clients.ScrapperClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class TrackCommand implements Command {
    @Autowired
    ScrapperClient scrapperClient;

    private static final String NAME = "/track";
    private static final String DESCRIPTION = "добавить ссылку в отслеживаемые";
    private static final String INVALID_ARG = "Некорректный аргумент";
    private static final String REQUEST_MSG = "Введите ссылку, которую хотите отслеживать";
    private static final String SUCCESS_MSG = "Ссылка добавлена";
    private static final String[] LINKS_PREFIX = {
        "https://github.com",
        "https://stackoverflow.com/"
    };

    @Override
    public String handle(Update update) {
        Long chatId = update.message().chat().id();
        scrapperClient.setStatus(chatId, 1L);
        return REQUEST_MSG;
    }

    public String trackLink(Update update) {
        URI url = null;
        Long chatId = update.message().chat().id();
        String link = update.message().text();

        try {
            if (!checkPrefix(link)) {
                return INVALID_ARG;
            }

            url = new URI(link);
        } catch (URISyntaxException e) {
            return INVALID_ARG;
        }

        scrapperClient.setStatus(chatId, 0L);
        scrapperClient.addLink(chatId, url);
        return SUCCESS_MSG;
    }

    private boolean checkPrefix(String url) {
        for (String pref : LINKS_PREFIX) {
            if (url.startsWith(pref)) {
                return true;
            }
        }

        return false;
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
