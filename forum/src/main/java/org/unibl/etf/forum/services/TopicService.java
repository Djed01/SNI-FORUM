package org.unibl.etf.forum.services;

import org.unibl.etf.forum.models.entities.TopicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.forum.repositories.TopicRepository;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public List<TopicEntity> findAll() {
        return topicRepository.findAll();
    }

    public TopicEntity findById(Integer id) {
        return topicRepository.findById(id).orElse(null);
    }

    public TopicEntity save(TopicEntity topic) {
        return topicRepository.save(topic);
    }

    public void deleteById(Integer id) {
        topicRepository.deleteById(id);
    }

}