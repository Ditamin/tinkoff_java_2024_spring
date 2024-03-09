package edu.java.bot.configuration;

import edu.java.bot.clients.ScrapperClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    @Bean
    public ScrapperClientImpl scrapperClient() {
        return new ScrapperClientImpl();
    }
}
