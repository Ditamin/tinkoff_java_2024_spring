package edu.java.configuration;

import edu.java.clients.bot.BotClientImpl;
import edu.java.clients.github.GitHubClientImpl;
import edu.java.clients.stackoverflow.StackOverflowClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
    @Bean
    GitHubClientImpl gitHubClient() {
        return new GitHubClientImpl();
    }

    @Bean
    public StackOverflowClientImpl stackOverflowClient() {
        return new StackOverflowClientImpl();
    }

    @Bean BotClientImpl botClient() {
        return new BotClientImpl();
    }
}
