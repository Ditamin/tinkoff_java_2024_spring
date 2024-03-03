package edu.java.clients.bot;

import edu.java.model.LinkUpdateRequest;

public interface BotClient {
    String sendUpdateLink(LinkUpdateRequest linkUpdateRequest);
}
