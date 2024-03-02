package edu.java.response.stackoveflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StackOverFlowUser(@JsonProperty("display_name") String login) {
}
