package org.unibl.etf.forum.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.unibl.etf.forum.models.entities.CommentEntity;
import org.unibl.etf.forum.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "https://localhost:4200")
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentEntity>> getAllComments() {
        return ResponseEntity.ok(commentService.findAllComments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentEntity> getCommentById(@PathVariable Integer id) {
        return commentService.findCommentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CommentEntity> createComment(@RequestBody CommentEntity comment) {
        return ResponseEntity.ok(commentService.saveComment(comment));
    }
    @CrossOrigin(origins = "https://localhost:4200")
    @PutMapping("/{id}")
    public ResponseEntity<CommentEntity> updateComment(@PathVariable Integer id, @RequestBody CommentEntity comment) {
        if (!commentService.findCommentById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        comment.setId(id);
        return ResponseEntity.ok(commentService.saveComment(comment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer id) {
        if (!commentService.findCommentById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<List<CommentEntity>> getCommentsByTopic(@PathVariable Integer topicId) {
        List<CommentEntity> comments = commentService.findCommentsByTopicWithStatusTrue(topicId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/falseStatus/{topicId}")
    public ResponseEntity<List<CommentEntity>> getCommentsByTopicWithFalseStatus(@PathVariable Integer topicId) {
        List<CommentEntity> comments = commentService.findCommentsByTopicWithStatusFalse(topicId);
        return ResponseEntity.ok(comments);
    }

    @CrossOrigin(origins = "https://localhost:4200")
    @PutMapping("/{id}/status/true")
    public ResponseEntity<CommentEntity> changeStatusToTrue(@PathVariable Integer id) {
        Optional<CommentEntity> commentOptional = commentService.findCommentById(id);

        if (commentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        CommentEntity comment = commentOptional.get();
        comment.setStatus(true);
        CommentEntity updatedComment = commentService.saveComment(comment);
        return ResponseEntity.ok(updatedComment);
    }


}
