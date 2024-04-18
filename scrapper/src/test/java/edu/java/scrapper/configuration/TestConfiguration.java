package edu.java.scrapper.configuration;

import edu.java.clients.bot.BotClientImpl;
import edu.java.clients.github.GitHubClientImpl;
import edu.java.clients.stackoverflow.StackOverflowClientImpl;
import edu.java.domain.JdbcConnectionDao;
import edu.java.domain.JdbcLinkDao;
import edu.java.domain.JdbcTgChatRepository;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import static edu.java.scrapper.IntegrationTest.POSTGRES;

@Configuration
@Profile("test")
public class TestConfiguration {
//    @Bean
//    public DataSource dataSource() {
//        return DataSourceBuilder.create()
//            .url(POSTGRES.getJdbcUrl())
//            .username(POSTGRES.getUsername())
//            .password(POSTGRES.getPassword())
//            .build();
//    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplate() {
//        return new JdbcTemplate(dataSource());
//    }
//
//    @Bean
//    public JdbcConnectionDao jdbcConnectionDao() {
//        return new JdbcConnectionDao(jdbcTemplate());
//    }
//
//    @Bean
//    public JdbcLinkDao jdbcLinkDao() {
//        return new JdbcLinkDao(jdbcTemplate());
//    }
//
//    @Bean
//    public JdbcTgChatRepository jdbcTgChatRepository() {
//        return new JdbcTgChatRepository(jdbcTemplate());
//    }
}
