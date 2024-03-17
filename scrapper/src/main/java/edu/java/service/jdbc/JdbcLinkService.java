package edu.java.service.jdbc;

import edu.java.domain.JdbcConnectionDao;
import edu.java.domain.JdbcLinkDao;
import edu.java.domain.JdbcTgChatRepository;
import edu.java.model.Link;
import edu.java.service.LinkService;
import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinkService implements LinkService {
    @Autowired
    private JdbcLinkDao linkDao;
    @Autowired
    private JdbcConnectionDao connectionDao;
    @Autowired
    private JdbcTgChatRepository tgChatRepository;

    @Override
    public Link add(long chatId, URI url) {
        if (tgChatRepository.find(chatId) == null) {
            tgChatRepository.add(chatId);
        }

        Link link = linkDao.find(url);

        if (link == null) {
            link = linkDao.add(url);
        }

        if (!connectionDao.find(chatId, link.id())) {
            connectionDao.add(chatId, link.id());
        }

        return link;
    }

    @Override
    public Link remove(long chatId, URI url) {
        Link link = linkDao.find(url);

        if (link == null) {
            return null;
        }

        if (!connectionDao.find(chatId, link.id())) {
            return null;
        }

        connectionDao.delete(chatId, link.id());
        return link;
    }

    @Override
    public List<Link> listAll(long chatId) {
        if (tgChatRepository.find(chatId) == null) {
            throw new NoSuchElementException();
        }

        return connectionDao.findAllLinks(chatId);
    }
}
