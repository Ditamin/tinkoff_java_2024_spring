package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.response.SendResponse;
import edu.java.bot.UpdateNoticeBot;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommandsHandlerTest {
    UpdateNoticeBot bot = new UpdateNoticeBot(System.getenv("TELEGRAMBOT_TOKEN"));
    Long chatId = 1781258823L;

    @Test
    void startCommandTest() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/start");
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(chatId);
        when(update.message().chat().id()).thenReturn(chatId);
        SendResponse response = bot.getCommandsHandler().handle(update);
        Assertions.assertThat(response.message().text()).isEqualTo(StartCommand.handle());
    }

    @Test
    void helpCommandTest() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/help");
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(chatId);
        when(update.message().chat().id()).thenReturn(chatId);
        SendResponse response = bot.getCommandsHandler().handle(update);
        Assertions.assertThat(response.message().text()).isEqualTo(HelpCommand.handle());
    }

    @Test
    void trackCommandTest() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/track");
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(chatId);
        when(update.message().chat().id()).thenReturn(chatId);
        SendResponse response = bot.getCommandsHandler().handle(update);
        Assertions.assertThat(response.message().text()).isEqualTo(TrackCommand.handle());
    }

    @Test
    void untrackCommandTest() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/untrack");
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(chatId);
        when(update.message().chat().id()).thenReturn(chatId);
        SendResponse response = bot.getCommandsHandler().handle(update);
        Assertions.assertThat(response.message().text()).isEqualTo(UntrackCommand.handle());
    }

    @Test
    void listCommandTest() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/list");
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(chatId);
        when(update.message().chat().id()).thenReturn(chatId);
        SendResponse response = bot.getCommandsHandler().handle(update);
        Assertions.assertThat(response.message().text()).isEqualTo(ListCommand.handle());
    }
}
