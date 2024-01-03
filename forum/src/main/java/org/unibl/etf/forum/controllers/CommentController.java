package org.unibl.etf.forum.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.forum.models.entities.CommentEntity;
import org.unibl.etf.forum.services.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public List<CommentEntity> getAllComments() {
        return commentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentEntity> getCommentById(@PathVariable Integer id) {
        CommentEntity comment = commentService.findById(id);
        if (comment != null) {
            return ResponseEntity.ok(comment);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public CommentEntity createComment(@RequestBody CommentEntity comment) {
        return commentService.save(comment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentEntity> updateComment(@PathVariable Integer id, @RequestBody CommentEntity commentDetails) {
        CommentEntity comment = commentService.findById(id);
        if (comment != null) {
            // Update the properties of comment with commentDetails
            return ResponseEntity.ok(commentService.save(comment));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer id) {
        if (commentService.findById(id) != null) {
            commentService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
