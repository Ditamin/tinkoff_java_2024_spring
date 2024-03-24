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
        client = WebClient.builder().build();
    }

    private static final String SITE = "site";
    private static final String SITE_NAME = "stackoverflow";

    @Override
    public StackOverFlowResponse fetchUpdates(Long questionId) {
        int commentCount = client.get()
            .uri(uriBuilder -> uriBuilder
                .scheme("https")
                .host("api.stackexchange.com")
                .pathSegment("2.3", "questions", questionId.toString(), "comments")
                .queryParam(SITE, SITE_NAME)
                .build())
            .retrieve()
            .bodyToMono(StackOverFlowResult.class)
            .block()
            .item()
            .size();

        StackOverFlowResult response = client.get()
            .uri(uriBuilder -> uriBuilder
                .scheme("https")
                .host("api.stackexchange.com")
                .pathSegment("2.3", "questions", questionId.toString())
                .queryParam(SITE, SITE_NAME)
                .build())
            .retrieve()
            .bodyToMono(StackOverFlowResult.class)
            .block();

        return new StackOverFlowResponse(response.item(), (long) commentCount);
    }
}
