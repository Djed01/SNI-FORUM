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

    @Autowired
    private TopicService topicService;

    @GetMapping("/")
    public List<TopicEntity> getAllTopics() {
        return topicService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicEntity> getTopicById(@PathVariable Integer id) {
        TopicEntity topic = topicService.findById(id);
        if (topic != null) {
            return ResponseEntity.ok(topic);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public TopicEntity createTopic(@RequestBody TopicEntity topic) {
        return topicService.save(topic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicEntity> updateTopic(@PathVariable Integer id, @RequestBody TopicEntity topicDetails) {
        TopicEntity topic = topicService.findById(id);
        if (topic != null) {
            // Update the properties of topic with topicDetails
            return ResponseEntity.ok(topicService.save(topic));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTopic(@PathVariable Integer id) {
        if (topicService.findById(id) != null) {
            topicService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}