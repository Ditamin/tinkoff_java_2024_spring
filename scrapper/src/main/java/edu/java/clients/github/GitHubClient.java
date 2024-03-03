package edu.java.clients.github;

import edu.java.dto.github.GitHubResponse;
import java.net.URISyntaxException;

public interface GitHubClient {
    GitHubResponse fetchUpdates(String user, String repository) throws URISyntaxException;
}
