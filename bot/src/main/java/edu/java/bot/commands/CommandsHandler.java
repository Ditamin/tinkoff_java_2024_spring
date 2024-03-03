package edu.java.bot.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CommandsHandler {
    private final TelegramBot bot;
    @Getter
    static Command[] commands;
    private final static String UNKNOWN_CMD = "Неизвестная команда. Введите /help для просмотра доступных команд";

    public CommandsHandler(TelegramBot bot) {
        this.bot = bot;
        addCommands();
        addCommandMenu();
    }

    private void addCommands() {
        commands = new Command[] {
            new StartCommand(),
            new HelpCommand(),
            new TrackCommand(),
            new UntrackCommand(),
            new ListCommand() };
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
        return new ArrayList<>(List.of(
            new StartCommand().getName(),
            new HelpCommand().getName()
        ));
    }

    public SendResponse handle(Update update) {
        if (update != null && update.message() != null && update.message().text() != null) {
            String response = makeResponse(update);
            SendMessage request = makeRequest(update, response);
            return bot.execute(request);
        }

        return null;
    }

    private String makeResponse(Update update) {
        String incomingCommand = update.message().text().split(" ")[0];

        for (Command command : commands) {
            if (Objects.equals(incomingCommand, command.getName())) {
                return command.handle();
            }
        }

        return UNKNOWN_CMD;
    }

    private SendMessage makeRequest(Update update, String response) {
        return new SendMessage(update.message().chat().id(), response)
            .parseMode(ParseMode.HTML)
            .disableWebPagePreview(true)
            .disableNotification(true)
            .replyToMessageId(update.message().messageId());
    }
}
