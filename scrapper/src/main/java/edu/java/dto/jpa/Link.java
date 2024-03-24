package edu.java.dto.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.net.URI;
import java.time.OffsetDateTime;
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
    Long linkId;
    @NonNull
    @Column(name = "url")
    URI url;
    @Column(name = "updated_at")
    OffsetDateTime updatedAt = OffsetDateTime.now();
    @Column(name = "answer_count")
    Long answerCount = 0L;
    @Column(name = "comment_count")
    Long commentCount = 0L;
    @ManyToMany(mappedBy = "links")
    Set<Link> chats;
}
