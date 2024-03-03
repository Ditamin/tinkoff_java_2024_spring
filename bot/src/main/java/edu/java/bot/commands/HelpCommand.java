package edu.java.bot.commands;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    private static final String NAME = "/help";
    private static final String DESCRIPTION = "вывести список доступных команд";

    private static String HELP_MESSAGE = "Доступные команды:\n\n";

    @Override
    public String handle() {
        StringBuilder text = new StringBuilder();

        for (Command command : CommandsHandler.getCommands()) {
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
