package edu.java.domain.jpa;

import edu.java.model.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface JpaTgChatRepository extends JpaRepository<Chat, Long> {
}
