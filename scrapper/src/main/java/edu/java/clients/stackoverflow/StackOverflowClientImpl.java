package edu.java.clients.stackoverflow;

import edu.java.dto.stackoveflow.StackOverFlowResponse;
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

    @Override
    public StackOverFlowResponse fetchUpdates(Long questionId) {
        return client.get()
            .uri(uriBuilder -> uriBuilder
                .path(String.format("/questions/%s", questionId))
                .queryParam("site", "stackoverflow")
                .build())
            .retrieve()
            .bodyToMono(StackOverFlowResponse.class)
            .block();
    }
}
