package edu.java.clients.bot;


import edu.java.dto.github.GitHubResponse;
import edu.java.model.requests.LinkUpdateRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.net.URISyntaxException;

@Service
public class BotClientImpl implements BotClient {

    private final WebClient client;

    @Value("${bot.baseUrl}")
    String baseUrl;
    @Value("${bot.retryAmount")
    int retryAmount;

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

    public String trySendUpdateLink(LinkUpdateRequest linkUpdateRequest) {
        int attempts = 1;

        while (attempts < retryAmount) {
            try {
                return sendUpdateLink(linkUpdateRequest);
            } catch (Exception e) {
                ++attempts;
            }
        }

        return sendUpdateLink(linkUpdateRequest);
    }
}
