package edu.java.domain.jdbc;

import edu.java.model.entity.Link;
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

    private static final String UTC = "UTC";

    public JdbcLinkDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @SuppressWarnings("MagicNumber")
    private Link getLink(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM links WHERE id = ?",
            (resultSet, rowNum) -> new Link(
                resultSet.getInt(1),
                URI.create(resultSet.getString(2)),
                OffsetDateTime.ofInstant(Instant.ofEpochMilli(resultSet.getTimestamp(3).getTime()),
                    ZoneId.of(UTC)),
                resultSet.getLong(4),
                resultSet.getLong(5)),
            id);
    }

    @Transactional
    public Link add(URI url) {
        log.info("Adding url = " + url + " to links");

        Integer id = jdbcTemplate.queryForObject("SELECT MAX(id) FROM links", Integer.class);

        if (id == null) {
            id = 0;
        }

        jdbcTemplate.update("INSERT INTO links (id, url) values (?, ?)", ++id, url.toString());
        return getLink(id);
    }

    @Transactional
    public Link delete(Integer linkId) {
        log.info("Удаление ссылки " + linkId + " из links");

        Link link = getLink(linkId);

        if (link == null) {
            return null;
        }

        jdbcTemplate.update("DELETE FROM links WHERE id = ?", link.getLinkId());
        return link;
    }

    public Link find(URI url) {
        log.info("Поиск url = " + url + " в links");

        try {
            Integer id = jdbcTemplate.queryForObject("SELECT id FROM links WHERE url = ?",
                Integer.class, url.toString());
            return getLink(id);

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Transactional
    public void update(Link link) {
        log.info("Обновление ссылки " + link.getUrl());

        jdbcTemplate.update("UPDATE links SET updated_at = ?, answer_count = ? WHERE id = ?",
            link.getUpdatedAt(), link.getAnswerCount(), link.getLinkId());
    }

    private final int linkLimit = 2;

    @SuppressWarnings("MagicNumber")
    public List<Link> findDeprecated() {
        log.info("Поиск давно не обновлявшихся ссылок");

        return jdbcTemplate.query(
            "SELECT * FROM links ORDER BY updated_at LIMIT ?",
            (resultSet, rowNum) -> new Link(
                resultSet.getInt(1),
                URI.create(resultSet.getString(2)),
                OffsetDateTime.ofInstant(
                    Instant.ofEpochMilli(resultSet.getTimestamp(3).getTime()),
                    ZoneId.of(UTC)),
                resultSet.getLong(4),
                resultSet.getLong(5)
            ),
            linkLimit
        );
    }
}
