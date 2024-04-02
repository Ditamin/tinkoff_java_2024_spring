package edu.java.scrapper.JdbcMethodsTest;

import edu.java.domain.jdbc.JdbcTgChatRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.util.NoSuchElementException;

@SpringBootTest
@ActiveProfiles("test")
public class JdbcChatTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTgChatRepository chatRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        Long chatId = 1L;

        chatRepository.add(chatId);
        Long res = jdbcTemplate.queryForObject("SELECT id FROM chats", Long.class);

        Assertions.assertThat(res).isEqualTo(chatId);
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        Long chatId = 1L;

        chatRepository.add(chatId);
        chatRepository.delete(chatId);
        Integer amount = jdbcTemplate.queryForObject("SELECT count(*) FROM chats", Integer.class);

        Assertions.assertThat(amount).isEqualTo(0);
    }

    @Test
    @Transactional
    @Rollback
    void removeNotExistedTest() {
        Long chatId = 1L;

        Assertions.assertThatThrownBy(() -> {
            chatRepository.delete(chatId);
        }).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @Transactional
    @Rollback
    void findTest() {
        Long chatId = 1L;

        chatRepository.add(chatId);
        Long chat = chatRepository.find(chatId);

        Assertions.assertThat(chat).isEqualTo(chatId);
    }

    @Test
    void findNotExistedTest() {
        Long chatId = 1L;

        Long chat = chatRepository.find(chatId);

        Assertions.assertThat(chat).isEqualTo(null);
    }
}
