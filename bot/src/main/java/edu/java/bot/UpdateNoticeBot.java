package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.commands.CommandsHandler;
import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateNoticeBot implements UpdatesListener {
    @Autowired
    TelegramBot bot;
    @Autowired
    CommandsHandler commandsHandler;


    public UpdateNoticeBot() {
        bot.setUpdatesListener(this);
    }

    public UpdateNoticeBot(TelegramBot telegramBot) {
        bot = telegramBot;
        bot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> list) {
        for (Update update : list) {
            commandsHandler.handle(update);
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
