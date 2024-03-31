package edu.java.domain.jpa;

import edu.java.model.entity.Link;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLinkDao extends JpaRepository<Link, Long> {
    Optional<Link> findByUrl(String url);
}
