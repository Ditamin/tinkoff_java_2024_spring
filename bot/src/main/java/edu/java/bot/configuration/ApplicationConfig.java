package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.UpdateNoticeBot;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.CommandsHandler;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UntrackCommand;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    @Value("${app.telegram-token}")
    String telegramToken
) {

    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot(telegramToken);
    }

    @Bean
    public Command[] commands() {
        return new Command[] {
            new StartCommand(),
            new HelpCommand(),
            new TrackCommand(),
            new UntrackCommand(),
            new ListCommand() };
    }

    @Bean
    public CommandsHandler commandsHandler() {
        return new CommandsHandler(telegramBot(), commands());
    }

    @Bean
    public UpdateNoticeBot updateNoticeBot() {
        return new UpdateNoticeBot(telegramBot());
    }
}
