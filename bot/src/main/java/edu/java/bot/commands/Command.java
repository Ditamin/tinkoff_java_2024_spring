package edu.java.bot.commands;

import lombok.Getter;

public interface Command {
    String handle();
    String getName();
    String getDescription();
}
