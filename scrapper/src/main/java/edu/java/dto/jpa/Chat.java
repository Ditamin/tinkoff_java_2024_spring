package edu.java.dto.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "chats")
public class Chat {
    @Id
    @Column(name = "id")
    Long chatId;
    @Column(name = "status")
    Long status;
    @ManyToMany
    @JoinTable(
        name = "connections",
        joinColumns = @JoinColumn(name = "chatId"),
        inverseJoinColumns = @JoinColumn(name = "linkId"))
    Set<Link> links;
}
