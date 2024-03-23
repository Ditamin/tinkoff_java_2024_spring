package edu.java.domain;

import java.util.NoSuchElementException;
import edu.java.exceptions.AlreadyRegisteredChatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class JdbcTgChatRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    JdbcConnectionDao connectionDao;

    public JdbcTgChatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void add(Long chatId) {
        log.info("Добавление чата  " + chatId + " в chats");

        jdbcTemplate.update("INSERT INTO chats (id) values (?)", chatId);
    }

    @Transactional
    public void delete(Long chatId) {
        log.info("Удаление чата " + chatId + " из chats");

        if (find(chatId) == null) {
            throw new NoSuchElementException();
        }

        connectionDao.deleteAllLinks(chatId);
        jdbcTemplate.update("DELETE FROM chats WHERE id = ?", chatId);
    }

    @Transactional
    public Long find(Long chatId) {
        log.info("Поиск чата " + chatId + " в links");

        try {
            return jdbcTemplate.queryForObject("SELECT id FROM chats WHERE id = ?", Long.class, chatId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Transactional
    public void setStatus(Long chatId, long status) {
        log.info("Чат " + chatId + " меняет статус");

        jdbcTemplate.update("UPDATE chats SET status = ? WHERE id = ?", status, chatId);
    }

    @Transactional
    public long getStatus(Long chatId) {
        log.info("Получение статуса чата " + chatId);

        return jdbcTemplate.queryForObject("SELECT status FROM chats WHERE id = ?", Long.class, chatId);
    }
}
