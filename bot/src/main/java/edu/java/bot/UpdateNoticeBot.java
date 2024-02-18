package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import edu.java.bot.commands.CommandsHandler;
import java.util.List;
import lombok.Getter;

public class UpdateNoticeBot {
    private final TelegramBot bot;
    @Getter private final CommandsHandler commandsHandler;

    private final static int UPDATE_LIMITS = 10;
    private final static int UPDATE_TIMEOUT = 10000;

    public UpdateNoticeBot(String token) {
        this.bot = new TelegramBot(token);
        this.commandsHandler = new CommandsHandler(bot);
        addCommandMenu();
    }

    private void addCommandMenu() {
        bot.execute(new SetMyCommands(
            new BotCommand("/track", "add link to tracked"),
            new BotCommand("/untrack", "remove link from tracked"),
            new BotCommand("/list", "show list of tracked")));
    }

    public void run() {
        int lastUpdateId = 0;

        while (true) {
            GetUpdates getUpdates = new GetUpdates().limit(UPDATE_LIMITS).offset(lastUpdateId).timeout(UPDATE_TIMEOUT);
            GetUpdatesResponse updatesResponse = bot.execute(getUpdates);
            List<Update> updates = updatesResponse.updates();

            for (Update update : updates) {
                commandsHandler.handle(update);
                lastUpdateId = update.updateId() + 1;
            }
        }
    }
}
