package edu.java.service.jpa;

import edu.java.domain.jpa.JpaTgChatRepository;
import edu.java.exceptions.AlreadyRegisteredChatException;
import edu.java.model.entity.Chat;
import edu.java.service.TgChatService;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JpaTgChatService implements TgChatService {
    @Autowired
    JpaTgChatRepository tgChatRepository;

    @Override
    public void register(long chatId) {
        if (tgChatRepository.existsById(chatId)) {
            throw new AlreadyRegisteredChatException();
        }

        Chat chat = new Chat();
        chat.setChatId(chatId);
        tgChatRepository.save(chat);
    }

    @Override
    public void unregister(long chatId) {
        if (!tgChatRepository.existsById(chatId)) {
            throw new NoSuchElementException();
        }

        tgChatRepository.deleteById(chatId);
    }

    @Override
    public void setStatus(long chatId, long status) {
        Chat chat = tgChatRepository.findById(chatId).orElseThrow(NoSuchElementException::new);
        chat.setStatus(status);
        tgChatRepository.save(chat);
    }

    @Override
    public long getStatus(long chatId) {
        Chat chat = tgChatRepository.findById(chatId).orElseThrow(NoSuchElementException::new);
        return chat.getStatus();
    }
}
