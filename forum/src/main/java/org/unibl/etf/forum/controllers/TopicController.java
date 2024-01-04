package org.unibl.etf.forum.controllers;

import org.unibl.etf.forum.models.entities.TopicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.forum.services.TopicService;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public ResponseEntity<List<TopicEntity>> getAllTopics() {
        return ResponseEntity.ok(topicService.findAllTopics());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicEntity> getTopicById(@PathVariable Integer id) {
        return topicService.findTopicById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TopicEntity> createTopic(@RequestBody TopicEntity topic) {
        return ResponseEntity.ok(topicService.saveTopic(topic));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicEntity> updateTopic(@PathVariable Integer id, @RequestBody TopicEntity topic) {
        if (!topicService.findTopicById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        topic.setId(id);
        return ResponseEntity.ok(topicService.saveTopic(topic));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTopic(@PathVariable Integer id) {
        if (!topicService.findTopicById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        topicService.deleteTopic(id);
        return ResponseEntity.ok().build();
    }
}
