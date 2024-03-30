package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    @Autowired
    Command[] commands;

    private static final String NAME = "/help";
    private static final String DESCRIPTION = "вывести список доступных команд";
    private static final String HELP_MESSAGE = "Доступные команды:\n\n";

    @Override
    public String handle(Update update) {
        StringBuilder text = new StringBuilder();

        for (Command command : commands) {
            text.append(command.getName()).append(" - ").append(command.getDescription()).append("\n");
        }

        return HELP_MESSAGE + text;
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
