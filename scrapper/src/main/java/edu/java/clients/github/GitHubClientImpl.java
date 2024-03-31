package edu.java.clients.github;

import edu.java.dto.github.GitHubResponse;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GitHubClientImpl implements GitHubClient {

    private final WebClient client;

    @Value("${github.baseUrl}")
    String baseUrl;
    @Value("${github.retryAmount")
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


    public GitHubResponse tryFetchUpdates(String user, String repository) throws URISyntaxException {
        int attempts = 1;

        while (attempts < retryAmount) {
            try {
                return fetchUpdates(user, repository);
            } catch (Exception e) {
                ++attempts;
            }
        }

        return fetchUpdates(user, repository);
    }
}
