package edu.java.clients.bot;

import edu.java.model.requests.LinkUpdateRequest;

public interface BotClient {
    String sendUpdateLink(LinkUpdateRequest linkUpdateRequest);
}
