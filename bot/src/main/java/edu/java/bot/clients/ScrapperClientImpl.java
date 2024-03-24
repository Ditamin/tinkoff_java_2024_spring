package edu.java.bot.clients;

import edu.java.bot.model.AddLinkRequest;
import edu.java.bot.model.LinkResponse;
import edu.java.bot.model.ListLinksResponse;
import edu.java.bot.model.RemoveLinkRequest;
import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class ScrapperClientImpl implements ScrapperClient {

    private final WebClient client;
    private final static String TG_CHAT_URI = "/tg-chat/{id}";
    private final static String LINKS_URI = "/links";
    private final static String STATUS = "/status";
    private final static String TG_CHAT_HEADER = "Tg-Chat-Id";

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
    public String setStatus(Long id, Long status) {
        return client.post()
            .uri(baseUrl + TG_CHAT_URI + STATUS + "/{status}", id, status)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    @Override
    public Long getStatus(Long id) {
        return client.get()
            .uri(baseUrl + TG_CHAT_URI + STATUS, id)
            .retrieve()
            .bodyToMono(Long.class)
            .block();
    }

    @Override
    public String addChat(Long id) {
        return client.post()
            .uri(baseUrl + TG_CHAT_URI, id)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    @Override
    public String deleteChat(Long id) {
        return client.delete()
            .uri(baseUrl + TG_CHAT_URI, id)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    @Override
    public ListLinksResponse getLinks(Long id) {
        return client.get()
            .uri(baseUrl + LINKS_URI)
            .header(TG_CHAT_HEADER, id.toString())
            .retrieve()
            .bodyToMono(ListLinksResponse.class)
            .block();
    }

    @Override
    public LinkResponse addLink(Long id, URI url) {
        return client.post()
            .uri(baseUrl + LINKS_URI)
            .header(TG_CHAT_HEADER, id.toString())
            .body(Mono.just(new AddLinkRequest(url)), AddLinkRequest.class)
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
    }

    @Override
    public LinkResponse deleteLink(Long id, URI url) {
        try {
            return client
                .method(HttpMethod.DELETE)
                .uri(baseUrl + LINKS_URI)
                .header(TG_CHAT_HEADER, id.toString())
                .body(Mono.just(new RemoveLinkRequest(url)), RemoveLinkRequest.class)
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
        } catch (WebClientResponseException e) {
            return null;
        }
    }
}
