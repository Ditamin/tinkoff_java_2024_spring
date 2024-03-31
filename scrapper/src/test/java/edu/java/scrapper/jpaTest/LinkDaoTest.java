package edu.java.scrapper.jpaTest;

import edu.java.domain.jpa.JpaLinkDao;
import edu.java.model.entity.Link;
import edu.java.scrapper.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

public class LinkDaoTest extends IntegrationTest {
    @Autowired JpaLinkDao linkDao;

    @Test
    @Transactional
    @Rollback
    public void findByUrlTest() {
        String url = "http://example.com";
        Link link = new Link(url);
        linkDao.save(link);
        Link linkInDao = linkDao.findByUrl(url).get();
        Assertions.assertThat(link).isEqualTo(linkInDao);
    }

    @Test
    @Transactional
    @Rollback
    public void findByUrlNotExistedTest() {
        String url = "http://example.com";
        Optional<Link> linkInDao = linkDao.findByUrl(url);
        Assertions.assertThat(linkInDao.isEmpty());
    }

    @Test
    public void injectionTest() {
        Assertions.assertThat(linkDao).isNotNull();
    }
}
