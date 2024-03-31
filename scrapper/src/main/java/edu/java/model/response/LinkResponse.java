package edu.java.model.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.net.URI;

public record LinkResponse(
    @NotNull
    Integer id,
    @NotBlank
    URI url
) {
}
