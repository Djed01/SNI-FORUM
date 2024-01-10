package org.unibl.etf.forum.repositories;

import org.unibl.etf.forum.models.entities.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findByTopicIdAndStatusTrue(Integer topicId);
    List<CommentEntity> findByTopicIdAndStatusFalse(Integer topicId);
}