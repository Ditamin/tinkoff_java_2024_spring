package edu.java.bot.commands;

public interface Command {

    String handle();

    String getName();

    String getDescription();
}
