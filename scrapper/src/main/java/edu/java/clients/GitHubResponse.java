package edu.java.clients;

import java.time.OffsetTime;

public record GitHubResponse(
    Long id,
    String name,
    String author,
    OffsetTime lastUpdate
) {
}
