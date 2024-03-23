package edu.java.bot.model;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import java.net.URI;

public record AddLinkRequest(
    @NotBlank
    URI link
) {
}
