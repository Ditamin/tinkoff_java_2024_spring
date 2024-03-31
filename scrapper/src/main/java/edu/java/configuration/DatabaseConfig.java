package edu.java.configuration;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class DatabaseConfig {
    @Value("${db.baseUrl}")
    String baseUrl;
    @Value("${db.username}")
    String username;
    @Value("${db.password}")
    String password;

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .url(baseUrl)
            .username(username)
            .password(password)
            .build();
    }
}
