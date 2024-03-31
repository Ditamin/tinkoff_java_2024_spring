package edu.java.controller;

import edu.java.exceptions.AlreadyRegisteredChatException;
import edu.java.model.requests.AddLinkRequest;
import edu.java.model.entity.Link;
import edu.java.model.response.LinkResponse;
import edu.java.model.response.ListLinksResponse;
import edu.java.model.requests.RemoveLinkRequest;
import edu.java.service.jdbc.JdbcLinkService;
import edu.java.service.jdbc.JdbcTgChatService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    JdbcLinkService linkService;
    @Autowired
    JdbcTgChatService tgChatService;

    private final static String NOT_EXISTED_CHAT_MSG = "Чат не существует";

    @GetMapping("/tg-chat/{id}/status")
    public Long getStatus(@PathVariable("id") Long id) {
        log.info("Запрос на получения статуса чата");

        return tgChatService.getStatus(id);
    }

    @PostMapping("/tg-chat/{id}/status/{status}")
    public String changeStatus(@PathVariable("id") Long id, @PathVariable("status") Long status) {
        log.info("Запрос на изменения статуса чата");

        tgChatService.setStatus(id, status);
        return "Статус изменен";
    }

    @PostMapping("/tg-chat/{id}")
    public String addChat(@PathVariable("id") Long id) {
        log.info("Запрос на регистрацию чата " + id);

        try {
            tgChatService.register(id);

        } catch (AlreadyRegisteredChatException e) {
            throw new AlreadyRegisteredChatException();
        }

        return "Чат зарегистрирован";
    }

    @DeleteMapping("/tg-chat/{id}")
    public String deleteChat(@PathVariable("id") Long id) {
        log.info("Запрос на удаление чата " + id);

        try {
            tgChatService.unregister(id);

        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(NOT_EXISTED_CHAT_MSG);
        }

        return "Чат успешно удалён";
    }

    @GetMapping("/links")
    public ListLinksResponse getLinks(@RequestHeader("Tg-Chat-Id") Long chatId) {
        log.info("Запрос на получение всех ссылок");

        List<Link> links = null;

        try {
            links = linkService.listAll(chatId);

        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(NOT_EXISTED_CHAT_MSG);
        }

        return new ListLinksResponse(links, links.size());
    }

    @PostMapping("/links")
    public LinkResponse addLink(@RequestHeader("Tg-Chat-Id") Long chatId,
        @RequestBody @Valid AddLinkRequest request) {
        log.info("Запрос на добавление ссылки");

        Link link = null;

        try {
            link = linkService.add(chatId, request.link());

        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(NOT_EXISTED_CHAT_MSG);
        }


        return new LinkResponse(link.getLinkId(), link.getUrl());
    }

    @DeleteMapping("/links")
    public LinkResponse deleteLink(@RequestHeader("Tg-Chat-Id") Long chatId,
        @RequestBody @Valid RemoveLinkRequest request) {
        log.info("Запрос на удаление ссылки");

        Link link = null;

        try {
            link = linkService.remove(chatId, request.link());

        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(NOT_EXISTED_CHAT_MSG);
        }

        return new LinkResponse(link.getLinkId(), link.getUrl());
    }
}
