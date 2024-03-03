package edu.java.bot.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import edu.java.bot.UpdateNoticeBot;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommandsHandlerTest {
    UpdateNoticeBot bot = new UpdateNoticeBot(System.getenv("TELEGRAMBOT_TOKEN"));
    TelegramBot telegramBot = mock(TelegramBot.class);
    Update update = mock(Update.class);
    Message message = mock(Message.class);
    Chat chat = mock(Chat.class);
    Long chatId = 1L; //1781258823L;
    CommandsHandler commandsHandler = new CommandsHandler(telegramBot);

    @BeforeEach
    void setUp() {
        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(message.chat().id()).thenReturn(chatId);
        when(update.message().chat().id()).thenReturn(chatId);
    }

    @Test
    void startCommandTest() {
        when(message.text()).thenReturn("/start");

        String response = commandsHandler.makeResponse(update);
        Assertions.assertThat(response).isEqualTo(new StartCommand().handle());
    }

    @Test
    void helpCommandTest() {
        when(message.text()).thenReturn("/help");

        String response = commandsHandler.makeResponse(update);
        Assertions.assertThat(response).isEqualTo(new HelpCommand().handle());
    }

    @Test
    void trackCommandTest() {
        when(message.text()).thenReturn("/track");

        String response = commandsHandler.makeResponse(update);
        Assertions.assertThat(response).isEqualTo(new TrackCommand().handle());
    }

    @Test
    void untrackCommandTest() {
        when(message.text()).thenReturn("/untrack");

        String response = commandsHandler.makeResponse(update);
        Assertions.assertThat(response).isEqualTo(new UntrackCommand().handle());
    }

    @Test
    void listCommandTest() {
        when(message.text()).thenReturn("/list");

        String response = commandsHandler.makeResponse(update);
        Assertions.assertThat(response).isEqualTo(new ListCommand().handle());
    }
}
