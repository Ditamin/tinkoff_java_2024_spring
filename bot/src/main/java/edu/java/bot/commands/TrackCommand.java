package edu.java.bot.commands;

import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command {
    private static final String NAME = "/track";
    private static final String DESCRIPTION = "добавить ссылку в отслеживаемые";
    private static final String INVALID_ARG = "Некорректный аргумент";

    @Override
    public String handle() {
        return "Не реализовано";
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
