package edu.java.dto.stackoveflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StackOverFlowResponse(
    @JsonProperty("items") List<StackOverFlowItem> item,
    Long commentAmount
) {
}
