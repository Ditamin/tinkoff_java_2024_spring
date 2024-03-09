package edu.java.controller;

import edu.java.exceptions.AlreadyRegisteredChatException;
import edu.java.model.AddLinkRequest;
import edu.java.model.LinkResponse;
import edu.java.model.ListLinksResponse;
import edu.java.model.RemoveLinkRequest;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ScrapperController {
    List<Integer> chatIds = new ArrayList<>();

    private final static String NOT_EXISTED_CHAT_MSG = "Чат не существует";

    @PostMapping("/tg-chat/{id}")
    public String addChat(@PathVariable("id") Integer id) {
        log.info("Запрос на регистрацию чата " + id);

        if (chatIds.contains(id)) {
            throw new AlreadyRegisteredChatException("Чат уже существует");
        }

        return "Чат зарегистрирован";
    }

    @DeleteMapping("/tg-chat/{id}")
    public String deleteChat(@PathVariable("id") Integer id) {
        log.info("Запрос на удаление чата " + id);

        if (!chatIds.contains(id)) {
            throw new NoSuchElementException(NOT_EXISTED_CHAT_MSG);
        }

        return "Чат успешно удалён";
    }

    @GetMapping("/links")
    public ListLinksResponse getLinks(@RequestHeader("Tg-Chat-Id") Integer chatId) {
        log.info("Запрос на получение всех ссылок");

        if (!chatIds.contains(chatId)) {
            throw new NoSuchElementException(NOT_EXISTED_CHAT_MSG);
        }

        return new ListLinksResponse(new ArrayList<>(), 0);
    }

    @PostMapping("/links")
    public LinkResponse addLink(@RequestHeader("Tg-Chat-Id") Integer chatId,
        @RequestBody @Valid AddLinkRequest request) {
        log.info("Запрос на добавление ссылки");

        if (!chatIds.contains(chatId)) {
            throw new NoSuchElementException(NOT_EXISTED_CHAT_MSG);
        }

        return new LinkResponse(0, request.link());
    }

    @DeleteMapping("/links")
    public LinkResponse deleteLink(@RequestHeader("Tg-Chat-Id") Integer chatId,
        @RequestBody @Valid RemoveLinkRequest request) {
        log.info("Запрос на удаление ссылки");

        if (!chatIds.contains(chatId)) {
            throw new NoSuchElementException(NOT_EXISTED_CHAT_MSG);
        }

        return new LinkResponse(0, request.link());
    }
}
