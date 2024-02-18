package edu.java.bot.commands;

public class ListCommand {
    private static final String LIST_EMPTY = "Список пуст";
    private static final String MESSAGE = "Отслеживаемые ссылки:\n";

    private ListCommand() {
    }

    public static String handle() {
        return LIST_EMPTY;
    }
}
