package edu.java.clients.bot;


import edu.java.model.LinkUpdateRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BotClientImpl implements BotClient {

    private final WebClient client;

    @Value("${bot.baseUrl}")
    String baseUrl;

    public BotClientImpl() {
        client = WebClient.builder().baseUrl(baseUrl).build();
    }

    public BotClientImpl(String baseUrl) {
        this.baseUrl = baseUrl;
        client = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public String sendUpdateLink(LinkUpdateRequest linkUpdateRequest) {
        return client.post()
            .uri(baseUrl + "/updates")
            .body(Mono.just(linkUpdateRequest), LinkUpdateRequest.class)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}
