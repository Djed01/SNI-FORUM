package org.unibl.etf.forum.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.forum.models.entities.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

}