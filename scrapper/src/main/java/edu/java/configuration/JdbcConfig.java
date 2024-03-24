package edu.java.configuration;

import edu.java.domain.jdbc.JdbcConnectionDao;
import edu.java.domain.jdbc.JdbcLinkDao;
import edu.java.domain.jdbc.JdbcTgChatRepository;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class JdbcConfig {
    @Value("${db.baseUrl}")
    String baseUrl;

    private final static String USERNAME = "postgres";

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
            .url(baseUrl)
            .username(USERNAME)
            .password(USERNAME)
            .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public JdbcConnectionDao jdbcConnectionDao() {
        return new JdbcConnectionDao(jdbcTemplate());
    }

    @Bean
    public JdbcLinkDao jdbcLinkDao() {
        return new JdbcLinkDao(jdbcTemplate());
    }

    @Bean
    public JdbcTgChatRepository jdbcTgChatRepository() {
        return new JdbcTgChatRepository(jdbcTemplate());
    }
}
