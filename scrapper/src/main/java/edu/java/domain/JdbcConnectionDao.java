package edu.java.domain;

import edu.java.model.Link;
import java.net.URI;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class JdbcConnectionDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    JdbcLinkDao jdbcLinkDao;

    public JdbcConnectionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void add(Long chatId, Long linkId) {
        log.info("Добавление ссылки " + linkId + " к чату " + chatId);

        jdbcTemplate.update("INSERT INTO connections (chat, link) values (?, ?)", chatId, linkId);
    }

    @Transactional
    public void delete(Long chatId, Long linkId) {
        log.info("Удаление " + linkId + " из чата " + chatId);

        jdbcTemplate.update("DELETE FROM connections WHERE chat = ? AND link = ?", chatId, linkId);

        Integer remain = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM connections WHERE link = ?",
            Integer.class,
            linkId);

        if (remain == 0) {
            jdbcLinkDao.delete(linkId);
        }
    }

    @Transactional
    public void deleteAllLinks(Long chatId) {
        log.info("Удаление всех связей ссылок с чатом " + chatId);

        jdbcTemplate.update("DELETE FROM connections WHERE chat = ?", chatId);
    }

    @Transactional
    public void deleteAllChats(Long linkId) {
        log.info("Удаление всех связей чатов с ссылкой " + linkId);

        jdbcTemplate.update("DELETE FROM connections WHERE link = ?", linkId);
    }

    @SuppressWarnings("MagicNumber")
    public List<Link> findAllLinks(Long chatId) {
        log.info("Поиск всех ссылок чата " + chatId);

        List<Long> linksId = jdbcTemplate.queryForList("SELECT link FROM connections WHERE chat = ?",
            Long.class, chatId);

        if (linksId.size() == 0) {
            return new ArrayList<>();
        }

        String inSql = String.join(",", Collections.nCopies(linksId.size(), "?"));

        return jdbcTemplate.query(String.format("SELECT * FROM links WHERE id IN (%s)", inSql),
            linksId.toArray(),
            (resultSet, rowNum) -> new Link(
                resultSet.getLong(1),
                URI.create(resultSet.getString(2)),
                OffsetDateTime.ofInstant(
                    Instant.ofEpochMilli(resultSet.getTimestamp(3).getTime()),
                    ZoneId.of("UTC")),
                resultSet.getLong(4),
                resultSet.getLong(5)));
    }

    public List<Long> findAllChats(Long linkId) {
        log.info("Поиск всех чатов с ссылкой " + linkId);

        return jdbcTemplate.queryForList("SELECT chat FROM connections WHERE link = ?", Long.class, linkId);
    }

    public boolean find(Long chatId, Long linkId) {
        log.info("Поиск связи ссылки " + linkId + " и чата = " + chatId);

        Integer amount = jdbcTemplate
            .queryForObject("SELECT COUNT(*) FROM connections WHERE chat = ? AND link = ?",
            Integer.class, chatId, linkId);

        return amount != 0;
    }
}
