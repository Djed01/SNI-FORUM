package org.unibl.etf.forum.services;

import org.unibl.etf.forum.models.entities.CommentEntity;
import org.unibl.etf.forum.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<CommentEntity> findAllComments() {
        return commentRepository.findAll();
    }

    public Optional<CommentEntity> findCommentById(Integer id) {
        return commentRepository.findById(id);
    }

    public CommentEntity saveComment(CommentEntity comment) {
        return commentRepository.save(comment);
    }

    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }

    public List<CommentEntity> findCommentsByTopic(Integer topicId) {
        return commentRepository.findByTopicId(topicId);
    }
}