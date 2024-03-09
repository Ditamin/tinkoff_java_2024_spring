package edu.java.bot.model;

import jakarta.validation.constraints.NotNull;
import java.net.URI;

public record LinkResponse(
    @NotNull
    Integer id,
    @NotNull
    URI url
) {
}
