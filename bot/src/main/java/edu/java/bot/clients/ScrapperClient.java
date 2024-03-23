package edu.java.bot.clients;

import edu.java.bot.model.LinkResponse;
import edu.java.bot.model.ListLinksResponse;
import java.net.URI;

public interface ScrapperClient {

    String addChat(Long id);

    String deleteChat(Long id);

    String setStatus(Long id, Long status);

    Long getStatus(Long id);

    ListLinksResponse getLinks(Long id);

    LinkResponse addLink(Long id, URI url);

    LinkResponse deleteLink(Long id, URI url);
}
