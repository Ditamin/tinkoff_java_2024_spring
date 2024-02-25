package edu.java.clients;

public interface GitHubClient {
    GitHubResponse fetchUpdates(String user, String repository);
}
