package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.commands.CommandsHandler;
import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class UpdateNoticeBot implements UpdatesListener {
    private final TelegramBot bot;
    @Getter private final CommandsHandler commandsHandler;


    public UpdateNoticeBot(String token) {
        this.bot = new TelegramBot(token);
        this.commandsHandler = new CommandsHandler(bot);
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
