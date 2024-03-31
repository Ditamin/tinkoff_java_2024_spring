package edu.java.clients.stackoverflow;

import edu.java.dto.github.GitHubResponse;
import edu.java.dto.stackoveflow.StackOverFlowResponse;
import edu.java.dto.stackoveflow.StackOverFlowResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.net.URISyntaxException;

@Component
public class StackOverflowClientImpl implements StackOverflowClient {

    private final WebClient client;
    private final static String URL_SCHEME = "https";
    private final static String URL_HOST = "api.stackexchange.com";
    private final static String URL_VERSION = "2.3";
    private final static String URL_PARAM_QUESTIONS = "questions";

    @Value("${stackoverflow.baseUrl}")
    String baseUrl;
    @Value("${stackoverflow.retryAmount")
    int retryAmount;

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

    public StackOverFlowResponse tryFetchUpdates(Long questionId) throws URISyntaxException {
        int attempts = 1;

        while (attempts < retryAmount) {
            try {
                return fetchUpdates(questionId);
            } catch (Exception e) {
                ++attempts;
            }
        }

        return fetchUpdates(questionId);
    }
}
