package edu.java.domain;

import edu.java.model.Link;
import java.net.URI;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class JdbcLinkDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    JdbcConnectionDao connectionDao;

    private static final String UTC = "UTC";

    public JdbcLinkDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @SuppressWarnings("MagicNumber")
    private Link getLink(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM links WHERE id = ?",
            (resultSet, rowNum) -> new Link(
                resultSet.getLong(1),
                URI.create(resultSet.getString(2)),
                OffsetDateTime.ofInstant(Instant.ofEpochMilli(resultSet.getTimestamp(3).getTime()),
                    ZoneId.of(UTC))),
            id);
    }

    @Transactional
    public Link add(URI url) {
        log.info("Adding url = " + url + " to links");

        Long id = jdbcTemplate.queryForObject("SELECT MAX(id) FROM links", Long.class);

        if (id == null) {
            id = 0L;
        }

        jdbcTemplate.update("INSERT INTO links (id, url) values (?, ?)", ++id, url.toString());
        return getLink(id);
    }

    @Transactional
    public Link delete(URI url) {
        log.info("Удаление url = " + url + " из links");

        Link link = find(url);

        if (link == null) {
            return null;
        }

        connectionDao.deleteAllChats(link.id());
        jdbcTemplate.update("DELETE FROM links WHERE id = ?", link.id());
        return link;
    }

    public Link find(URI url) {
        log.info("Поиск url = " + url + " в links");

        try {
            Long id = jdbcTemplate.queryForObject("SELECT id FROM links WHERE url = ?", Long.class, url.toString());
            return getLink(id);

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private final int linkLimit = 1;

    public List<Link> findDeprecated() {
        log.info("Поиск давно не обновлявшихся ссылок");

        return jdbcTemplate.query(
            "SELECT * FROM links ORDER BY updatedAt LIMIT ?",
            (resultSet, rowNum) -> new Link(
                resultSet.getLong("id"),
                URI.create(resultSet.getString("url")),
                OffsetDateTime.ofInstant(
                    Instant.ofEpochMilli(resultSet.getTimestamp("updatedAt").getTime()),
                    ZoneId.of(UTC)
                )
            ),
            linkLimit
        );
    }
}
