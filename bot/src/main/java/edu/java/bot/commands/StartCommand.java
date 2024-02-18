package edu.java.bot.commands;

public class StartCommand {
    private static final String DESCRIPTION = "Привет! Я бот, который может оповещать тебя об обновлении"
        + "отслеживаемых тобой статей и проектов на GitHub и Stack Overflow.\n\n";

    private StartCommand() {
    }

    public static String handle() {
        return DESCRIPTION + HelpCommand.handle();
    }
}
