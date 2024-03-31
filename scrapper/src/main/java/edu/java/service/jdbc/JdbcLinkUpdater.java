package edu.java.service.jdbc;

import edu.java.clients.bot.BotClientImpl;
import edu.java.clients.github.GitHubClientImpl;
import edu.java.clients.stackoverflow.StackOverflowClientImpl;
import edu.java.domain.jdbc.JdbcConnectionDao;
import edu.java.domain.jdbc.JdbcLinkDao;
import edu.java.dto.github.GitHubResponse;
import edu.java.dto.stackoveflow.StackOverFlowResponse;
import edu.java.model.entity.Link;
import edu.java.model.requests.LinkUpdateRequest;
import edu.java.service.LinkUpdater;
import java.net.URISyntaxException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
            if (Objects.equals(link.getUrl().getHost(), "github.com")) {
                gitHubHandler(link);

            } else if (Objects.equals(link.getUrl().getHost(), "stackoverflow.com")) {
                stackOverflowHandler(link);
            }
        }

        return 0;
    }

    void gitHubHandler(Link link) {
        String[] args = link.getUrl().getPath().split("/");
        String user = args[1];
        String repo = args[2];

        try {
            GitHubResponse response = gitHubClient.fetchUpdates(user, repo);

            if (response.lastUpdate().isAfter(link.getUpdatedAt())) {
                String msg = "Появилось новое изменение в репозитории "
                    + response.name() + " на GitHub у " + response.author().login();

                Link newLink = new Link(
                    link.getLinkId(),
                    link.getUrl(),
                    response.lastUpdate(),
                    link.getAnswerCount(),
                    link.getCommentCount()
                );

                linkDao.update(newLink);
                noticeBotClient(newLink, msg);
            }

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    void stackOverflowHandler(Link link) {
        String[] args = link.getUrl().getPath().split("/");
        Long questionId = Long.parseLong(args[2]);

        StackOverFlowResponse response = stackOverflowClient.fetchUpdates(questionId);

        Link newLink = new Link(
            link.getLinkId(),
            link.getUrl(),
            response.item().getFirst().lastUpdate(),
            response.item().getFirst().answerCount(),
            response.commentAmount());

        if (response.item().getFirst().lastUpdate().isAfter(link.getUpdatedAt())) {
            String msg = "Появилось новое изменение в вопросе на StackOverflow с темой "
                + response.item().getFirst().name();

            if (response.commentAmount() < link.getCommentCount()) {
                msg = "Появился новый комментарий в вопросе на StackOverflow с темой "
                    + response.item().getFirst().name();
            }

            if (response.item().getFirst().answerCount() < link.getAnswerCount()) {
                msg = "Появился новый ответ в вопросе на StackOverflow с темой "
                    + response.item().getFirst().name();
            }

            noticeBotClient(newLink, msg);
        }

        linkDao.update(newLink);
    }

    void noticeBotClient(Link link, String msg) {
        botClient.sendUpdateLink(
            new LinkUpdateRequest(link.getLinkId(),
                link.getUrl(),
                msg,
                connectionDao.findAllChats(link.getLinkId())));
    }
}
