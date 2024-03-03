package edu.java.model;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

public record RemoveLinkRequest(
    @NotBlank
    String link
) {
}
