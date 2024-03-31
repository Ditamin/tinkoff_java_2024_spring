package edu.java.bot.controller;

import edu.java.bot.commands.CommandsHandler;
import edu.java.bot.model.LinkUpdateRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UpdateController {
    @Autowired
    CommandsHandler commandsHandler;

    @PostMapping("/updates")
    public String updateLinks(@RequestBody @Valid LinkUpdateRequest linkUpdateRequest) {
        log.info("Запрос на обновление ссылок");

        for (Long chatId : linkUpdateRequest.tgChatIds()) {
            sendMessage(chatId, linkUpdateRequest.description());
        }

        return "Обновление обработано";
    }

    void sendMessage(Long chatId, String message) {
        commandsHandler.sendMessage(chatId, message);
    }
}
