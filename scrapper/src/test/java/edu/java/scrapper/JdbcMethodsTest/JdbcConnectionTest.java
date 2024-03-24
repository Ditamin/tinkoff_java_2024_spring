package edu.java.scrapper.JdbcMethodsTest;

import edu.java.domain.JdbcConnectionDao;
import edu.java.domain.JdbcLinkDao;
import edu.java.model.Link;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class JdbcConnectionTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcConnectionDao connectionDao;
    @Autowired
    private JdbcLinkDao linkDao;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        Long chatId = 1L;
        Long linkId = 2L;

        connectionDao.add(chatId, linkId);
        Long chatRes = jdbcTemplate.queryForObject("SELECT chat FROM connections", Long.class);
        Long linkRes = jdbcTemplate.queryForObject("SELECT link FROM connections", Long.class);

        Assertions.assertThat(chatRes).isEqualTo(chatId);
        Assertions.assertThat(linkRes).isEqualTo(linkId);
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        Long chatId = 1L;
        Long linkId = 2L;

        connectionDao.add(chatId, linkId);
        connectionDao.delete(chatId, linkId);

        Integer res = jdbcTemplate.queryForObject("SELECT count(*) FROM connections", Integer.class);
        Assertions.assertThat(res).isEqualTo(0);
    }

    @Test
    @Transactional
    @Rollback
    void removeAllLinksTest() {
        Long chatId = 1L;
        Long link1Id = 1L;
        Long link2Id = 2L;

        connectionDao.add(chatId, link1Id);
        connectionDao.add(chatId, link2Id);
        connectionDao.deleteAllLinks(chatId);

        Integer res = jdbcTemplate.queryForObject("SELECT count(*) FROM connections", Integer.class);
        Assertions.assertThat(res).isEqualTo(0);
    }

    @Test
    @Transactional
    @Rollback
    void removeAllChatsTest() {
        Long chat1Id = 1L;
        Long chat2Id = 2L;
        Long linkId = 1L;

        connectionDao.add(chat1Id, linkId);
        connectionDao.add(chat2Id, linkId);
        connectionDao.deleteAllChats(linkId);

        Integer res = jdbcTemplate.queryForObject("SELECT count(*) FROM connections", Integer.class);
        Assertions.assertThat(res).isEqualTo(0);
    }

    @Test
    @Transactional
    @Rollback
    void findAllLinksTest() {
        Long chatId = 1L;
        URI url1 = URI.create("http://example1.com");
        URI url2 = URI.create("http://example2.com");

        Link link1 = linkDao.add(url1);
        Link link2 = linkDao.add(url2);
        connectionDao.add(chatId, link1.id());
        connectionDao.add(chatId, link2.id());
        List<Link> all = connectionDao.findAllLinks(chatId);

        Assertions.assertThat(all.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @Rollback
    void findAllChatsTest() {
        Long chat1Id = 1L;
        Long chat2Id = 2L;
        Long linkId = 1L;

        connectionDao.add(chat1Id, linkId);
        connectionDao.add(chat2Id, linkId);
        List<Long> all = connectionDao.findAllChats(linkId);
        Integer res = jdbcTemplate.queryForObject("SELECT count(*) FROM connections", Integer.class);

        Assertions.assertThat(res).isEqualTo(2);
    }
}
