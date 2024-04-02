package edu.java.scrapper.configuration;

import edu.java.domain.jdbc.JdbcConnectionDao;
import edu.java.domain.jdbc.JdbcLinkDao;
import edu.java.domain.jdbc.JdbcTgChatRepository;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import static edu.java.scrapper.IntegrationTest.POSTGRES;

@TestConfiguration
@Profile("test")
public class TestConfig {
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .url(POSTGRES.getJdbcUrl())
            .username(POSTGRES.getUsername())
            .password(POSTGRES.getPassword())
            .build();
    }
}
