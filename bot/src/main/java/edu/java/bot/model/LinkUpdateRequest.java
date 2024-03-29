package edu.java.bot.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record LinkUpdateRequest(
    @NotNull
    Integer id,
    @NotBlank
    String url,
    String description,
    @NotEmpty
    List<Integer> tgChatIds
) {
}
