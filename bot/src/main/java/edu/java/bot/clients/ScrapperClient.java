package edu.java.bot.clients;

import edu.java.bot.model.LinkResponse;
import edu.java.bot.model.ListLinksResponse;

public interface ScrapperClient {
    String addChat(Integer id);
    String deleteChat(Integer id);
    ListLinksResponse getLinks(Integer id);
    LinkResponse addLink(Integer id);
    LinkResponse deleteLink(Integer id);
}
