package org.unibl.etf.forum.services;

import org.unibl.etf.forum.models.entities.TopicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.forum.repositories.TopicRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<TopicEntity> findAllTopics() {
        return topicRepository.findAll();
    }

    public Optional<TopicEntity> findTopicById(Integer id) {
        return topicRepository.findById(id);
    }

    public TopicEntity saveTopic(TopicEntity topic) {
        return topicRepository.save(topic);
    }

    public void deleteTopic(Integer id) {
        topicRepository.deleteById(id);
    }
}