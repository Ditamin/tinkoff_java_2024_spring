package edu.java.scrapper.jpaTest;

import edu.java.domain.jpa.JpaTgChatRepository;
import edu.java.exceptions.AlreadyRegisteredChatException;
import edu.java.scrapper.IntegrationTest;
import edu.java.service.jpa.JpaTgChatService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.util.NoSuchElementException;

public class TgChatServiceTest extends IntegrationTest {
    @Autowired
    JpaTgChatRepository tgChatRepository;
    @Autowired
    JpaTgChatService tgChatService;

    @Test
    @Transactional
    @Rollback
    public void registerTest() {
        Long chatId = 1L;

        tgChatService.register(chatId);
        Long chatCount = tgChatRepository.count();

        Assertions.assertThat(chatCount).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void registerTwiceTest() {
        Long chatId = 1L;

        tgChatService.register(chatId);

        Assertions.assertThatThrownBy(() -> {
            tgChatService.register(chatId);
        }).isInstanceOf(AlreadyRegisteredChatException.class);
    }

    @Test
    @Transactional
    @Rollback
    public void unregisterTest() {
        Long chatId = 1L;

        tgChatService.register(chatId);
        tgChatService.unregister(chatId);
        Long chatCount = tgChatRepository.count();

        Assertions.assertThat(chatCount).isEqualTo(0);
    }

    @Test
    @Transactional
    @Rollback
    public void unregisterNotExistedTest() {
        Long chatId = 1L;

        Assertions.assertThatThrownBy(() -> {
            tgChatService.unregister(chatId);
        }).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @Transactional
    @Rollback
    public void changeStatusTest() {
        Long chatId = 1L;

        tgChatService.register(chatId);
        tgChatService.setStatus(chatId, 1);
        Long status = tgChatService.getStatus(chatId);

        Assertions.assertThat(status).isEqualTo(1);
    }

    @Test
    @Transactional
    @Rollback
    public void getStatusNotExistedChatTest() {
        Long chatId = 1L;

        Assertions.assertThatThrownBy(() -> {
            tgChatService.unregister(chatId);
        }).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @Transactional
    @Rollback
    public void setStatusNotExistedChatTest() {
        Long chatId = 1L;

        Assertions.assertThatThrownBy(() -> {
            tgChatService.unregister(chatId);
        }).isInstanceOf(NoSuchElementException.class);
    }
}
