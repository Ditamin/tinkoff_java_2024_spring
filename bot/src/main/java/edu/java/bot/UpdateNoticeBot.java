package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandScope;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandScopeDefault;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import edu.java.bot.commands.CommandsHandler;
import edu.java.bot.commands.StartCommand;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UpdateNoticeBot {
    private final TelegramBot bot;
    private final CommandsHandler commandsHandler;

    private final static int updateLimits = 10;
    private final static int updateTimeout = 10000;

    public UpdateNoticeBot(String token) {
        this.bot = new TelegramBot(token);
        this.commandsHandler = new CommandsHandler(bot);

        List<BotCommand> listOfCommands = new ArrayList<>(List.of(
            new BotCommand("/track", "add link to tracked"),
            new BotCommand("/untrack", "remove link from tracked"),
            new BotCommand("/list", "show list of tracked")
        ));

        bot.execute(new SetMyCommands(listOfCommands.get(0), listOfCommands.get(1), listOfCommands.get(2)));
    }

    public void run() {
        int lastUpdateId = 0;

        while (true) {
            GetUpdates getUpdates = new GetUpdates().limit(updateLimits).offset(lastUpdateId).timeout(updateTimeout);
            GetUpdatesResponse updatesResponse = bot.execute(getUpdates);
            List<Update> updates = updatesResponse.updates();

            for (Update update : updates) {
                commandsHandler.handle(update);
                lastUpdateId = update.updateId() + 1;
            }
        }
    }

    public static void main(String[] args) {
        new UpdateNoticeBot("6765746692:AAHaiEEAVX-FgpIqHEhIWhbstMcPVCIAkEI").run();
    }
}
