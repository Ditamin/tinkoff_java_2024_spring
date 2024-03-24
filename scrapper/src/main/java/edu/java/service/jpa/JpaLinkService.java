package edu.java.service.jpa;

import edu.java.domain.jpa.JpaLinkDao;
import edu.java.dto.jpa.Chat;
import edu.java.model.Link;
import edu.java.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.util.Collection;

@Service
public class JpaLinkService implements LinkService {
    @Autowired JpaLinkDao linkDao;

    @Override
    public Link add(long tgChatId, URI url) {
        return null;
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        return null;
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        return null;
    }
}
