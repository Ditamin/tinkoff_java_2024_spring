package edu.java.model.response;

import edu.java.model.entity.Link;

import java.util.List;

public record ListLinksResponse(
    List<Link> links,
    Integer size
) {
}
