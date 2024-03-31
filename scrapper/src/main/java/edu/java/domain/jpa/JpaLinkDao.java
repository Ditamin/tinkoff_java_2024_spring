package edu.java.domain.jpa;

import edu.java.model.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import java.net.URI;
import java.util.Optional;

@Repository
public interface JpaLinkDao extends JpaRepository<Link, Long> {
    Optional<Link> findByUrl(String url);
}
