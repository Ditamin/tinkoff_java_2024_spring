package edu.java.bot.configuration;

import edu.java.bot.UpdateNoticeBot;
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
    public UpdateNoticeBot updateNoticeBot() {
        return new UpdateNoticeBot(telegramToken);
    }
}
