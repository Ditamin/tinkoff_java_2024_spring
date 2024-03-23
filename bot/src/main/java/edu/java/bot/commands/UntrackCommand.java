package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import edu.java.bot.clients.ScrapperClient;
import edu.java.bot.model.LinkResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class UntrackCommand implements Command {
    @Autowired
    ScrapperClient scrapperClient;

    private static final String NAME = "/untrack";
    private static final String DESCRIPTION = "убрать ссылку из отслеживаемых";
    private static final String INVALID_ARG = "Некорректный аргумент";
    private static final String REQUEST_MSG = "Введите ссылку, которую хотите убрать";
    private static final String SUCCESS_MSG = "Ссылка удалена";
    private static final String NOT_EXISTED_MSG = "Ссылка не найдена";
    private static final String[] LINKS_PREFIX = {
        "https://github.com",
        "https://stackoverflow.com/"
    };

    @Override
    public String handle(Update update) {
        Long chatId = update.message().chat().id();
        scrapperClient.setStatus(chatId, 2L);
        return REQUEST_MSG;
    }

    public String untrackLink(Update update) {
        URI url = null;
        String link = update.message().text();

        try {
            if (!checkPrefix(link)) {
                return INVALID_ARG;
            }

            url = new URI(update.message().text());
        } catch (URISyntaxException e) {
            return INVALID_ARG;
        }

        Long chatId = update.message().chat().id();
        scrapperClient.setStatus(chatId, 0L);
        LinkResponse response = scrapperClient.deleteLink(chatId, url);

        if (response != null) {
            return SUCCESS_MSG;
        }

        return NOT_EXISTED_MSG;
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
