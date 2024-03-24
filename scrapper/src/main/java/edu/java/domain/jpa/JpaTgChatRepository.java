package edu.java.domain.jpa;

import edu.java.dto.jpa.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTgChatRepository extends JpaRepository<Chat, Long> {
}
