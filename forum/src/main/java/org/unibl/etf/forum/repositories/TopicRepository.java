package org.unibl.etf.forum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.forum.models.entities.TopicEntity;

public interface TopicRepository extends JpaRepository<TopicEntity, Integer> {

}