package edu.java.scrapper.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

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
