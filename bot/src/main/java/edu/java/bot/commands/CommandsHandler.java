package edu.java.bot.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;

public class CommandsHandler {
    private final TelegramBot bot;

    public CommandsHandler(TelegramBot bot) {
        this.bot = bot;
    }

    private final static String UNKNOW_CMD = "Неизвестная команда. Введите /help для просмотра доступных команд";
    private final static String START_CMD = "/start";
    private final static String HELP_CMD = "/help";
    private final static String TRACK_CMD = "/track";
    private final static String UNTRACK_CMD = "/untrack";
    private final static String LIST_CMD = "/list";



    public void handle(Update update) {
        if (update != null && update.message() != null) {
            String response = switch(update.message().text()) {
                case START_CMD -> StartCommand.handle();
                case HELP_CMD -> HelpCommand.handle();
                case TRACK_CMD -> TrackCommand.handle();
                case UNTRACK_CMD -> UntrackCommand.handle();
                case LIST_CMD -> ListCommand.handle();
                default -> UNKNOW_CMD;
            };

            SendMessage request = new SendMessage(update.message().chat().id(), response)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true)
                .disableNotification(true)
                .replyToMessageId(update.message().messageId());

            bot.execute(request);
        }
    }
}
