package edu.java.bot.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.SendResponse;
import edu.java.bot.clients.ScrapperClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandsHandler {
    @Autowired
    TelegramBot bot;
    @Autowired
    TrackCommand trackCommand;
    @Autowired
    UntrackCommand untrackCommand;
    @Autowired
    ScrapperClient scrapperClient;

    @Getter
    @Autowired
    Command[] commands;

    private final static String UNKNOWN_CMD = "Неизвестная команда. Введите /help для просмотра доступных команд";
    private final static Long TRACK_STATUS = 1L;
    private final static Long UNTRACK_STATUS = 2L;

    public CommandsHandler(TelegramBot telegramBot, Command[] commands) {
        this.bot = telegramBot;
        this.commands = commands;
        addCommandMenu();
    }

    private void addCommandMenu() {
        ArrayList<BotCommand> commandToAdd = new ArrayList<>();
        ArrayList<String> redundantCommands = getRedundantCommands();

        for (Command command : commands) {
            boolean isReduntant = false;

            for (String reduntantCommand : redundantCommands) {
                if (reduntantCommand.equals(command.getName())) {
                    isReduntant = true;
                    break;
                }
            }

            if (!isReduntant) {
                commandToAdd.add(new BotCommand(command.getName(), command.getDescription()));
            }
        }

        BotCommand[] commandArray = new BotCommand[commandToAdd.size()];

        for (int i = 0; i < commandToAdd.size(); ++i) {
            commandArray[i] = commandToAdd.get(i);
        }

        bot.execute(new SetMyCommands(commandArray));
    }

    private ArrayList<String> getRedundantCommands() {
        return new ArrayList<String>(List.of(
            new StartCommand().getName(),
            new HelpCommand().getName()
        ));
    }

    public SendResponse handle(Update update) {
        if (update != null && update.message() != null && update.message().text() != null) {
            String response = makeResponse(update);
            Long chatId = update.message().chat().id();
            return sendMessage(chatId, response);
        }

        return null;
    }

    public String makeResponse(Update update) {
        String incomingCommand = update.message().text().split(" ")[0];
        Long chatId = update.message().chat().id();

        for (Command command : commands) {
            if (Objects.equals(incomingCommand, command.getName())) {
                scrapperClient.setStatus(chatId, 0L);
                return command.handle(update);
            }
        }

        Long status = scrapperClient.getStatus(chatId);

        if (status.equals(TRACK_STATUS)) {
            return trackCommand.trackLink(update);
        }

        if (status.equals(UNTRACK_STATUS)) {
            return untrackCommand.untrackLink(update);
        }

        return UNKNOWN_CMD;
    }

    public SendMessage makeRequest(Long chatId, String response) {
        return new SendMessage(chatId, response)
            .parseMode(ParseMode.HTML)
            .disableWebPagePreview(true)
            .disableNotification(true);
    }

    public SendResponse sendMessage(Long chatId, String msg) {
        return bot.execute(makeRequest(chatId, msg));
    }
}
