package edu.java.domain.jpa;

import edu.java.dto.jpa.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLinkDao extends JpaRepository<Link, Long> {

}
