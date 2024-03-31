package edu.java.scrapper.jpaTest;

import edu.java.domain.jpa.JpaLinkDao;
import edu.java.exceptions.AlreadyAddedLinkException;
import edu.java.model.entity.Link;
import edu.java.scrapper.IntegrationTest;
import edu.java.service.jpa.JpaLinkService;
import edu.java.service.jpa.JpaTgChatService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.util.Collection;
import java.util.NoSuchElementException;

@SpringBootTest
@ActiveProfiles("test")
public class LinkServiceTest extends IntegrationTest {
    @Autowired
    JpaLinkService linkService;
    @Autowired
    JpaLinkDao linkDao;
    @Autowired
    JpaTgChatService tgChatService;

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        URI url = URI.create("http://example.com");
        Long chatId = 1L;

        tgChatService.register(chatId);
        linkService.add(chatId, url);
        long linkCount = linkDao.count();

        Assertions.assertThat(linkCount).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void addTwoChatWithOneLinkTest() {
        URI url = URI.create("http://example.com");
        Long chatId1 = 1L;
        Long chatId2 = 2L;

        tgChatService.register(chatId1);
        tgChatService.register(chatId2);
        linkService.add(chatId1, url);
        linkService.add(chatId2, url);
        long linkCount = linkDao.count();

        Assertions.assertThat(linkCount).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void addTwiceTest() {
        URI url = URI.create("http://example.com");
        Long chatId = 1L;

        tgChatService.register(chatId);
        linkService.add(chatId, url);

        Assertions.assertThatThrownBy(() -> {
            linkService.add(chatId, url);
        }).isInstanceOf(AlreadyAddedLinkException.class);
    }

    @Test
    @Transactional
    @Rollback
    public void addToNotExistedChatTest() {
        URI url = URI.create("http://example.com");
        Long chatId = 1L;

        Assertions.assertThatThrownBy(() -> {
            linkService.add(chatId, url);
        }).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() {
        URI url = URI.create("http://example.com");
        Long chatId = 1L;

        tgChatService.register(chatId);
        linkService.add(chatId, url);
        linkService.remove(chatId, url);
        long linkCount = linkDao.count();

        Assertions.assertThat(linkCount).isEqualTo(0);
    }

    @Test
    @Transactional
    @Rollback
    public void removeNotExistedTest() {
        URI url = URI.create("http://example.com");
        Long chatId = 1L;

        tgChatService.register(chatId);

        Assertions.assertThatThrownBy(() -> {
            linkService.remove(chatId, url);
        }).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @Transactional
    @Rollback
    public void removeFromNotExistedChatTest() {
        URI url = URI.create("http://example.com");
        Long chatId = 1L;

        Assertions.assertThatThrownBy(() -> {
            linkService.remove(chatId, url);
        }).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @Transactional
    @Rollback
    public void removeLinkWithTwoChatTest() {
        URI url = URI.create("http://example.com");
        Long chatId1 = 1L;
        Long chatId2 = 2L;

        tgChatService.register(chatId1);
        tgChatService.register(chatId2);
        linkService.add(chatId1, url);
        linkService.add(chatId2, url);

        linkService.remove(chatId1, url);
        long linkCount = linkDao.count();

        Assertions.assertThat(linkCount).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void listAllTest() {
        URI url1 = URI.create("http://example1.com");
        URI url2 = URI.create("http://example2.com");
        URI url3 = URI.create("http://example3.com");
        Long chatId1 = 1L;
        Long chatId2 = 2L;

        tgChatService.register(chatId1);
        tgChatService.register(chatId2);
        linkService.add(chatId1, url1);
        linkService.add(chatId1, url2);
        linkService.add(chatId2, url3);
        Collection<Link> links = linkService.listAll(chatId1);

        Assertions.assertThat(links.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    @Rollback
    public void listEmptyTest() {
        Long chatId = 1L;

        tgChatService.register(chatId);
        Collection<Link> links = linkService.listAll(chatId);

        Assertions.assertThat(links.size()).isEqualTo(0);
    }
}
