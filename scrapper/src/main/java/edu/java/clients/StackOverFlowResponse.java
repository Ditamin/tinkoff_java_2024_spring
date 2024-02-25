package edu.java.clients;

import java.time.OffsetTime;

public record StackOverFlowResponse(
    Long id,
    String name,
    String author,
    OffsetTime lastUpdate
) {
}
