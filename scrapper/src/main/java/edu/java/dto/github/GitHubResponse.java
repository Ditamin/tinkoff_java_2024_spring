package edu.java.dto.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GitHubResponse(
    Long id,
    @JsonProperty("owner")
    GitHubUser author,
    String name,
    @JsonProperty("updated_at")
    OffsetDateTime lastUpdate
) {
}
