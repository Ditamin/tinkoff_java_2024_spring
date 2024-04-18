package edu.java.scrapper;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

public class UpdateMigrationsTest extends IntegrationTest {
    @Test
    @Transactional
    @Rollback
    public void getChatIdTest() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSourceBuilder.create()
            .url(POSTGRES.getJdbcUrl())
            .username(POSTGRES.getUsername())
            .password(POSTGRES.getPassword())
            .build());

        jdbcTemplate.update("INSERT INTO chats VALUES(?)",1);
        Integer res = jdbcTemplate.queryForObject("SELECT count(*) FROM chats", Integer.class);
        Assertions.assertThat(res).isEqualTo(1);
    }
}
