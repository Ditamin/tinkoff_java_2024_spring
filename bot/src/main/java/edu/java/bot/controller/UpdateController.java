package edu.java.bot.controller;

import edu.java.bot.exceptions.AlreadyExistedLinkException;
import edu.java.bot.model.LinkUpdateRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@RestController
public class UpdateController {
    List<String> links;

    @PostMapping("/updates")
    public String updateLinks(@RequestBody @Valid LinkUpdateRequest linkUpdateRequest) {
        log.info("Запрос на обновление ссылок");

        if (links.contains(linkUpdateRequest.url())) {
            throw new AlreadyExistedLinkException("Ссылка уже добавлена");
        }

        return "Обновление обработано";
    }
}
