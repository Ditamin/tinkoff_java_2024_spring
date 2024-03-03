package edu.java.bot.commands;

import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
    private static final String NAME = "/start";
    private static final String DESCRIPTION = "начать работу с ботом";
    private static final String MESSAGE = "Привет! Я бот, который может оповещать тебя об обновленияx "
        + "отслеживаемых тобой статей и проектов на GitHub и Stack Overflow.";


    @Override
    public String handle() {
        return MESSAGE;
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
