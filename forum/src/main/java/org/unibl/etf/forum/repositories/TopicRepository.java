package org.unibl.etf.forum.repositories;

import org.unibl.etf.forum.models.entities.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, Integer> {
    // Add any custom queries here if necessary
}