package edu.java.bot.configuration;

import edu.java.bot.UpdateNoticeBot;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.StartCommand;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import java.util.ArrayList;
import java.util.List;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    @Value("${app.telegram-token}")
    String telegramToken
) {

    @Bean
    public UpdateNoticeBot updateNoticeBot() {
        return new UpdateNoticeBot(telegramToken);
    }
}
