package edu.java.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "chats")
public class Chat {
    @Id
    @Column(name = "id")
    Long chatId;
    @Column(name = "status")
    Long status = 0L;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
        name = "connections",
        joinColumns = @JoinColumn(name = "chat"),
        inverseJoinColumns = @JoinColumn(name = "link"))
    Set<Link> links = new HashSet<>();
}
