package edu.java.scrapper.JdbcMethodsTest;

import edu.java.domain.jdbc.JdbcLinkDao;
import edu.java.model.entity.Link;
import edu.java.scrapper.IntegrationTest;
import edu.java.scrapper.configuration.TestConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;

public class JdbcLinkTest extends IntegrationTest {
    @Autowired
    private JdbcLinkDao linkRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        URI url = URI.create("http://example.com");

        Link res = linkRepository.add(url);
        Integer amount = jdbcTemplate.queryForObject("SELECT count(*) FROM links", Integer.class);

        Assertions.assertThat(amount).isEqualTo(1);
        Assertions.assertThat(res.getUrl()).isEqualTo(url);
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        URI url = URI.create("http://example.com");

        Link link = linkRepository.add(url);
        link = linkRepository.delete(link.getLinkId());
        Integer res = jdbcTemplate.queryForObject("SELECT count(*) FROM links", Integer.class);

        Assertions.assertThat(res).isEqualTo(0);
        Assertions.assertThat(link.getUrl()).isEqualTo(url);
    }

    @Test
    @Transactional
    @Rollback
    void removeNotExistedTest() {
        URI url = URI.create("http://example.com");

        Link link = linkRepository.delete(1);

        Assertions.assertThat(link).isEqualTo(null);
    }

    @Test
    @Transactional
    @Rollback
    void findTest() {
        URI url = URI.create("http://example.com");

        linkRepository.add(url);
        Link res = linkRepository.find(url);

        Assertions.assertThat(res.getUrl()).isEqualTo(url);
    }

    @Test
    void findNotExistedTest() {
        URI url = URI.create("http://example.com");

        Link res = linkRepository.find(url);

        Assertions.assertThat(res).isEqualTo(null);
    }
}
