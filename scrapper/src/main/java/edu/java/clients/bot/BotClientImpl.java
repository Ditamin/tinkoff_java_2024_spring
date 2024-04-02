package edu.java.clients.bot;


import edu.java.dto.github.GitHubResponse;
import edu.java.model.requests.LinkUpdateRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.net.URISyntaxException;
import java.util.Objects;
import static java.lang.Math.min;

@Service
public class BotClientImpl implements BotClient {

    private final WebClient client;

    @Value("${bot.baseUrl}")
    String baseUrl;
    @Value("${bot.retryAmount}")
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

    @Value("${bot.policy}")
    String policy;

    private final static int MIN_DELAY = 100;
    private final static int MAX_DELAY = 1000_000;
    private final static int FACTOR = 2;

    public String trySendUpdateLink(LinkUpdateRequest linkUpdateRequest) throws InterruptedException {
        int attempts = 1;
        int delay = MIN_DELAY;

        while (attempts < retryAmount) {
            try {
                return sendUpdateLink(linkUpdateRequest);
            } catch (Exception e) {
                ++attempts;
                Thread.sleep(getDelay(delay));
            }
        }

        return sendUpdateLink(linkUpdateRequest);
    }

    private int getDelay(int delay) {
        if (Objects.equals(policy, "linear")) {
            delay = min(delay + MIN_DELAY, MAX_DELAY);
        }

        if (Objects.equals(policy, "exponent")) {
            delay = min(delay * FACTOR, MAX_DELAY);
        }

        return delay;
    }
}
