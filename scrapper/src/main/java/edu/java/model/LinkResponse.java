package edu.java.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LinkResponse(
    @NotNull
    Integer id,
    @NotBlank
    String url
) {
}
