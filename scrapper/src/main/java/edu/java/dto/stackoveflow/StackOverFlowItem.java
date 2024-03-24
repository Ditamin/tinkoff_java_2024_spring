package edu.java.dto.stackoveflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StackOverFlowItem(
    @JsonProperty("question_id")
    Long id,
    @JsonProperty("owner")
    StackOverFlowUser author,
    @JsonProperty("title")
    String name,
    @JsonProperty("last_activity_date")
    OffsetDateTime lastUpdate,
    @JsonProperty("answer_count")
    Long answerCount
) {
}
