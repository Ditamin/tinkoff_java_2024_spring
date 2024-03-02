package edu.java.scrapper.clientsTest;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Body;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.clients.github.GitHubClientImpl;
import edu.java.response.github.GitHubResponse;
import edu.java.response.github.GitHubUser;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;

public class GitHubClientTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8081);

    @Test
    public void correctResponseTest() throws URISyntaxException {
        GitHubResponse mustReturn = new GitHubResponse(
            1L,
            new GitHubUser("mylogin"),
            "myname",
            OffsetDateTime.parse("2024-02-20T12:00:00Z"));

        String responseBody = """
                {
                    "id": 1,
                    "name": "myname",
                    "owner": {
                        "login": "mylogin"
                    },
                    "created_at": "2024-01-20T12:00:00Z",
                    "updated_at": "2024-02-20T12:00:00Z",
                    "pushed_at": "2024-03-20T12:00:00Z"
                }
            """;

        stubFor(get(urlEqualTo("/repos/mylogin/myname"))
            .willReturn(aResponse()
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(responseBody)));

        GitHubClientImpl client = new GitHubClientImpl("http://localhost:8081");
        GitHubResponse response = client.fetchUpdates("mylogin", "myname");

        Assertions.assertThat(response).isEqualTo(mustReturn);
    }

    @Test
    public void emptyResponseTest() throws URISyntaxException {
        String responseBody = "";

        stubFor(get(urlEqualTo("/repos/mylogin/myname"))
            .willReturn(aResponse()
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(responseBody)));

        GitHubClientImpl client = new GitHubClientImpl("http://localhost:8081");
        GitHubResponse response = client.fetchUpdates("mylogin", "myname");

        Assertions.assertThat(response).isEqualTo(null);
    }
}
