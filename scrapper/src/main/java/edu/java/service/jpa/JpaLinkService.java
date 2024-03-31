package edu.java.service.jpa;

import edu.java.domain.jpa.JpaLinkDao;
import edu.java.domain.jpa.JpaTgChatRepository;
import edu.java.exceptions.AlreadyAddedLinkException;
import edu.java.model.entity.Chat;
import edu.java.model.entity.Link;
import edu.java.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.util.Collection;
import java.util.NoSuchElementException;

//@Service
public class JpaLinkService implements LinkService {
    @Autowired
    JpaLinkDao linkDao;
    @Autowired
    JpaTgChatRepository tgChatRepository;

    @Override
    public Link add(long tgChatId, URI url) {
        Chat chat = tgChatRepository.findById(tgChatId).orElseThrow();
        Link link = linkDao.findByUrl(url.toString()).orElse(null);

        if (link == null) {
            link = new Link(url.toString());
            linkDao.save(link);
        }

        if (chat.getLinks().contains(link)) {
            throw new AlreadyAddedLinkException();
        }

        chat.getLinks().add(link);
        tgChatRepository.save(chat);
        return link;
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        Chat chat = tgChatRepository.findById(tgChatId).orElseThrow();
        Link link = linkDao.findByUrl(url.toString()).orElseThrow();

        if (!chat.getLinks().contains(link)) {
            throw new NoSuchElementException();
        }

        chat.getLinks().remove(link);
        tgChatRepository.save(chat);

        if (link.getChats().isEmpty()) {
            linkDao.delete(link);
        }

        return link;
    }

    @Override
    public Collection<Link> listAll(long tgChatId) {
        Chat chat = tgChatRepository.findById(tgChatId).orElseThrow(NoSuchElementException::new);
        return chat.getLinks();
    }
}
