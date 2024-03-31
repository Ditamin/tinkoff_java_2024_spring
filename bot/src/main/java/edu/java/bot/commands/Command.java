package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;

public interface Command {

    String handle(Update update);

    String getName();

    String getDescription();
}
