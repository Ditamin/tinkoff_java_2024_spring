package edu.java.service.jdbc;

import edu.java.clients.bot.BotClientImpl;
import edu.java.clients.github.GitHubClientImpl;
import edu.java.clients.stackoverflow.StackOverflowClientImpl;
import edu.java.domain.JdbcConnectionDao;
import edu.java.domain.JdbcLinkDao;
import edu.java.dto.github.GitHubResponse;
import edu.java.dto.stackoveflow.StackOverFlowResponse;
import edu.java.model.Link;
import edu.java.model.LinkUpdateRequest;
import edu.java.service.LinkUpdater;
import java.net.URISyntaxException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;

public class JdbcLinkUpdater implements LinkUpdater {
    @Autowired
    private JdbcLinkDao linkDao;
    @Autowired
    private JdbcConnectionDao connectionDao;
    @Autowired
    private StackOverflowClientImpl stackOverflowClient;
    @Autowired
    private GitHubClientImpl gitHubClient;
    @Autowired
    private BotClientImpl botClient;

    @Override
    public int update() {
        for (Link link : linkDao.findDeprecated()) {
            if (Objects.equals(link.url().getHost(), "github.com")) {
                gitHubHandler(link);

            } else if (Objects.equals(link.url().getHost(), "stackoverflow.com")) {
                stackOverflowHandler(link);
            }
        }

        return 0;
    }

    void gitHubHandler(Link link) {
        String[] args = link.url().getPath().split("/");
        String user = args[0];
        String repo = args[1];

        try {
            GitHubResponse response = gitHubClient.fetchUpdates(user, repo);

            if (response.lastUpdate().isAfter(link.updatedAt())) {
                noticeBotClient(link);
            }

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    void stackOverflowHandler(Link link) {
        String[] args = link.url().getPath().split("/");
        Long questionId = Long.parseLong(args[0]);

        StackOverFlowResponse response = stackOverflowClient.fetchUpdates(questionId);

        if (response.item().getFirst().lastUpdate().isAfter(link.updatedAt())) {
            noticeBotClient(link);
        }
    }

    void noticeBotClient(Link link) {
        botClient.sendUpdateLink(
            new LinkUpdateRequest(link.id(),
                link.url(),
                "Обновление",
                connectionDao.findAllChats(link.id())));
    }
}
