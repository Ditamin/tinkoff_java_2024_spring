package edu.java.scrapper.clientsTest;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Body;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.clients.github.GitHubClientImpl;
import edu.java.clients.stackoverflow.StackOverflowClient;
import edu.java.clients.stackoverflow.StackOverflowClientImpl;
import edu.java.response.github.GitHubResponse;
import edu.java.response.github.GitHubUser;
import edu.java.response.stackoveflow.StackOverFlowItem;
import edu.java.response.stackoveflow.StackOverFlowResponse;
import edu.java.response.stackoveflow.StackOverFlowUser;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

public class StackOverFlowClientTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8081);

    @Test
    public void correctResponseTest() throws URISyntaxException {
        StackOverFlowResponse mustReturn = new StackOverFlowResponse(List.of(new StackOverFlowItem(
            1L,
            new StackOverFlowUser("mylogin"),
            "myname",
            OffsetDateTime.parse("2024-02-20T12:00:00Z"))));

        String responseBody = """
                {
                    "items": [
                        {
                            "owner": {
                                "display_name": "mylogin"
                            },
                            "last_activity_date": 1708430400,
                            "creation_date": 1707110425,
                            "question_id": 1,
                            "title": "myname"
                        }
                    ]
                }
            """;

        stubFor(get("/questions/1?site=stackoverflow")
            .willReturn(aResponse()
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(responseBody)));

        StackOverflowClientImpl client = new StackOverflowClientImpl("http://localhost:8081");
        StackOverFlowResponse response = client.fetchUpdates(1L);

        Assertions.assertThat(response).isEqualTo(mustReturn);
    }

    @Test
    public void emptyResponseTest() throws URISyntaxException {
        String responseBody = "";

        stubFor(get("/questions/1?site=stackoverflow")
            .willReturn(aResponse()
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(responseBody)));

        StackOverflowClientImpl client = new StackOverflowClientImpl("http://localhost:8081");
        StackOverFlowResponse response = client.fetchUpdates(1L);

        Assertions.assertThat(response).isEqualTo(null);
    }
}
