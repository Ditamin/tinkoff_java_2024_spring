package edu.java.model.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

public record LinkUpdateRequest(
    @NotNull
    Integer id,
    @NotNull
    URI url,
    String description,
    @NotEmpty
    List<Long> tgChatIds
) {
}
