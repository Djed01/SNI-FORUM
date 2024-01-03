package org.unibl.etf.forum.services;

import org.unibl.etf.forum.models.entities.CommentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.forum.repositories.CommentRepository;

import java.util.List;
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<CommentEntity> findAll() {
        return commentRepository.findAll();
    }

    public CommentEntity findById(Integer id) {
        return commentRepository.findById(id).orElse(null);
    }

    public CommentEntity save(CommentEntity comment) {
        return commentRepository.save(comment);
    }

    public void deleteById(Integer id) {
        commentRepository.deleteById(id);
    }
}
