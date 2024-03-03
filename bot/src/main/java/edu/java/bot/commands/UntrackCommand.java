package edu.java.bot.commands;

import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements Command {
    private static final String NAME = "/untrack";
    private static final String DESCRIPTION = "убрать ссылку из отслеживаемых";
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
