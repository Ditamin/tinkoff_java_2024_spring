package edu.java.model;

import java.net.URI;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

public record RemoveLinkRequest(
    @NotBlank
    URI link
) {
}
