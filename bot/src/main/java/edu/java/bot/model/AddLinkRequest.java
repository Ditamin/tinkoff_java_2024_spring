package edu.java.bot.model;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

public record AddLinkRequest(
    @NotBlank
    String link
) {
}
