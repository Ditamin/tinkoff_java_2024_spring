package edu.java.clients.stackoverflow;

import edu.java.dto.stackoveflow.StackOverFlowResponse;
import edu.java.dto.stackoveflow.StackOverFlowResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class StackOverflowClientImpl implements StackOverflowClient {

    private final WebClient client;

    @Value("${stackoverflow.baseUrl}")
    String baseUrl;

    public StackOverflowClientImpl() {
        client = WebClient.builder().baseUrl(baseUrl).build();
    }

    public StackOverflowClientImpl(String baseUrl) {
        this.baseUrl = baseUrl;
        client = WebClient.builder().baseUrl(baseUrl).build();
    }

    private static final String SITE = "SITE";
    private static final String SITE_NAME = "stackoverflow";

    @Override
    public StackOverFlowResponse fetchUpdates(Long questionId) {
        int commentCount = client.get()
            .uri(uriBuilder -> uriBuilder
                .path(String.format("/questions/%s/comments", questionId))
                .queryParam(SITE, SITE_NAME)
                .build())
            .retrieve()
            .bodyToMono(StackOverFlowResult.class)
            .block()
            .item()
            .size();

        StackOverFlowResult response = client.get()
            .uri(uriBuilder -> uriBuilder
                .path(String.format("/questions/%s", questionId))
                .queryParam(SITE, SITE_NAME)
                .build())
            .retrieve()
            .bodyToMono(StackOverFlowResult.class)
            .block();

        return new StackOverFlowResponse(response.item(), (long) commentCount);
    }
}
