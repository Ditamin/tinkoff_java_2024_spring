package edu.java.bot.clients;

import edu.java.bot.model.LinkResponse;
import edu.java.bot.model.ListLinksResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ScrapperClientImpl implements ScrapperClient {

    private final WebClient client;

    @Value("${scrapper.baseUrl}")
    String baseUrl;

    public ScrapperClientImpl() {
        client = WebClient.builder().baseUrl(baseUrl).build();
    }

    public ScrapperClientImpl(String baseUrl) {
        this.baseUrl = baseUrl;
        client = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public String addChat(Integer id) {
        return client.post()
            .uri("/tg-chat/{id}", id)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    @Override
    public String deleteChat(Integer id) {
        return client.delete()
            .uri("/tg-chat/{id}", id)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    @Override
    public ListLinksResponse getLinks(Integer id) {
        return client.get()
            .uri("/links")
            .retrieve()
            .bodyToMono(ListLinksResponse.class)
            .block();
    }

    @Override
    public LinkResponse addLink(Integer id) {
        return client.post()
            .uri("/links")
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }

    @Override
    public LinkResponse deleteLink(Integer id) {
        return client.delete()
            .uri("/links")
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }
}
