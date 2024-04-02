package edu.java.clients.github;

import edu.java.dto.github.GitHubResponse;
import java.net.URISyntaxException;
import java.util.Objects;
import edu.java.dto.stackoveflow.StackOverFlowResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import static java.lang.Math.min;

@Component
public class GitHubClientImpl implements GitHubClient {

    private final WebClient client;

    @Value("${github.baseUrl}")
    String baseUrl;
    @Value("${github.retryAmount}")
    int retryAmount;

    public GitHubClientImpl() {
        client = WebClient.builder().baseUrl(baseUrl).build();
    }

    public GitHubClientImpl(String baseUrl) {
        this.baseUrl = baseUrl;
        client = WebClient.builder().baseUrl(baseUrl).build();
    }

    @Override
    public GitHubResponse fetchUpdates(String user, String repository) throws URISyntaxException {
        return client.get()
            .uri(baseUrl + "/repos/{user}/{repo}", user, repository)
            .retrieve()
            .bodyToMono(GitHubResponse.class)
            .block();
    }

    @Value("${github.policy}")
    String policy;

    private final static int MIN_DELAY = 100;
    private final static int MAX_DELAY = 1000_000;
    private final static int FACTOR = 2;

    public GitHubResponse tryFetchUpdates(String user, String repository)
        throws InterruptedException, URISyntaxException {
        int attempts = 1;
        int delay = MIN_DELAY;

        while (attempts < retryAmount) {
            try {
                return fetchUpdates(user, repository);
            } catch (Exception e) {
                ++attempts;
                Thread.sleep(getDelay(delay));
            }
        }

        return fetchUpdates(user, repository);
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
