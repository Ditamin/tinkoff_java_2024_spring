package edu.java.model.requests;

import java.net.URI;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

public record AddLinkRequest(
    @NotBlank
    URI link
) {
}