package edu.java.bot.commands;

import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {
    private static final String NAME = "/list";
    private static final String DESCRIPTION = "вывести список отслеживаемых ссылок";
    private static final String LIST_EMPTY = "Список пуст";
    private static final String MESSAGE = "Отслеживаемые ссылки:\n";

    @Override
    public String handle() {
        return LIST_EMPTY;
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
