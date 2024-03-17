package edu.java.service.jdbc;

import edu.java.domain.JdbcTgChatRepository;
import edu.java.exceptions.AlreadyRegisteredChatException;
import edu.java.service.TgChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JdbcTgChatService implements TgChatService {
    @Autowired
    private JdbcTgChatRepository tgChatRepository;

    @Override
    public void register(long chatId) {
        log.info("Регистрация чата " + chatId);

        if (tgChatRepository.find(chatId) != null) {
            throw new AlreadyRegisteredChatException("Чат уже существует");
        }

        tgChatRepository.add(chatId);
    }

    @Override
    public void unregister(long chatId) {
        log.info("Удаление чата " + chatId);

        tgChatRepository.delete(chatId);
    }
}