package edu.java.model;

import java.util.List;

public record ListLinksResponse(
    List<Link> links,
    Integer size
) {
}
