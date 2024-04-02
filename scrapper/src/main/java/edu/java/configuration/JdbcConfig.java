package edu.java.configuration;

import edu.java.domain.jdbc.JdbcConnectionDao;
import edu.java.domain.jdbc.JdbcLinkDao;
import edu.java.domain.jdbc.JdbcTgChatRepository;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class JdbcConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
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
