package edu.java.clients.stackoverflow;

import edu.java.dto.github.GitHubResponse;
import edu.java.dto.stackoveflow.StackOverFlowResponse;
import edu.java.dto.stackoveflow.StackOverFlowResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.net.URISyntaxException;
import java.util.Objects;
import static java.lang.Math.min;

@Component
public class StackOverflowClientImpl implements StackOverflowClient {

    private final WebClient client;
    private final static String URL_SCHEME = "https";
    private final static String URL_HOST = "api.stackexchange.com";
    private final static String URL_VERSION = "2.3";
    private final static String URL_PARAM_QUESTIONS = "questions";

    @Value("${stackoverflow.baseUrl}")
    String baseUrl;
    @Value("${stackoverflow.retryAmount}")
    int retryAmount;
    @Value("${stackoverflow.policy}")
    String policy;

    private final static int MIN_DELAY = 100;
    private final static int MAX_DELAY = 1000_000;
    private final static int FACTOR = 2;

    public StackOverflowClientImpl() {
        client = WebClient.builder().baseUrl(baseUrl).build();
    }

    public StackOverflowClientImpl(String baseUrl) {
        this.baseUrl = baseUrl;
        client = WebClient.builder().build();
    }

    private static final String SITE = "site";
    private static final String SITE_NAME = "stackoverflow";

    @Override
    public StackOverFlowResponse fetchUpdates(Long questionId) {
        int commentCount = client.get()
            .uri(uriBuilder -> uriBuilder
                .scheme(URL_SCHEME)
                .host(URL_HOST)
                .pathSegment(URL_VERSION, URL_PARAM_QUESTIONS, questionId.toString(), "comments")
                .queryParam(SITE, SITE_NAME)
                .build())
            .retrieve()
            .bodyToMono(StackOverFlowResult.class)
            .block()
            .item()
            .size();

        StackOverFlowResult response = client.get()
            .uri(uriBuilder -> uriBuilder
                .scheme(URL_SCHEME)
                .host(URL_HOST)
                .pathSegment(URL_VERSION, URL_PARAM_QUESTIONS, questionId.toString())
                .queryParam(SITE, SITE_NAME)
                .build())
            .retrieve()
            .bodyToMono(StackOverFlowResult.class)
            .block();

        return new StackOverFlowResponse(response.item(), (long) commentCount);
    }

    public StackOverFlowResponse tryFetchUpdates(Long questionId) throws InterruptedException {
        int attempts = 1;
        int delay = MIN_DELAY;

        while (attempts < retryAmount) {
            try {
                return fetchUpdates(questionId);
            } catch (Exception e) {
                ++attempts;
                Thread.sleep(getDelay(delay));
            }
        }

        return fetchUpdates(questionId);
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
