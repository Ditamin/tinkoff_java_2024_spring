package edu.java.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "links")
public class Link {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer linkId;
    @NonNull
    @Column(name = "url")
    String url;
    @Column(name = "updated_at")
    OffsetDateTime updatedAt = OffsetDateTime.now();
    @Column(name = "answer_count")
    Long answerCount = 0L;
    @Column(name = "comment_count")
    Long commentCount = 0L;
    @ManyToMany(targetEntity = Chat.class, mappedBy = "links")
//    @JoinTable(
//        name = "connections",
//        joinColumns = @JoinColumn(name = "link"),
//        inverseJoinColumns = @JoinColumn(name = "chat"))
    Set<Link> chats = new HashSet<>();

    public Link(Integer linkId, URI url, OffsetDateTime updatedAt, Long answerCount, Long commentCount) {
        this.linkId = linkId;
        this.url = url.toString();
        this.updatedAt = updatedAt;
        this.answerCount = answerCount;
        this.commentCount = commentCount;
    }

    public URI getUrl() {
        return URI.create(url);
    }
}
