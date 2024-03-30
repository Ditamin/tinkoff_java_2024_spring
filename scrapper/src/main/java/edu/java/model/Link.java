package edu.java.model;

import java.net.URI;
import java.time.OffsetDateTime;

public record Link(
    Long id,
    URI url,
    OffsetDateTime updatedAt,
    Long answerAmount,
    Long commentAmount
) {
}
