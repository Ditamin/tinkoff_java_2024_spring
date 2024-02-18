package edu.java.bot.commands;

public class HelpCommand {
    private HelpCommand() {
    }

    private static final String HELP_MESSAGE = """
        Доступные команды:

        /help - выводит доступные команды
        /track (URL) добавляет ссылку в отслеживаемые
        /untrack (URL) удаляет ссылку из отслеживаемых
        /list выводит список отслеживаемых ссылок""";

    public static String handle() {
        return HELP_MESSAGE;
    }
}
