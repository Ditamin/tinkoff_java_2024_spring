package edu.java.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.net.URI;

public record LinkResponse(
    @NotNull
    Long id,
    @NotBlank
    URI url
) {
}
