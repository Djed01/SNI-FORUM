package org.unibl.etf.forum.services;

import org.unibl.etf.forum.models.entities.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.forum.models.entities.UserPermissionEntity;
import org.unibl.etf.forum.repositories.UserPermissionRepository;
import org.unibl.etf.forum.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserPermissionService {

    private final UserPermissionRepository userPermissionRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserPermissionService(UserPermissionRepository userPermissionRepository, UserRepository userRepository) {
        this.userPermissionRepository = userPermissionRepository;
        this.userRepository = userRepository;
    }

    public List<UserPermissionEntity> findByUserId(Integer userId) {
        return userPermissionRepository.findByUserId(userId);
    }

    public List<UserPermissionEntity> findByTopicId(Integer topicId) {
        return userPermissionRepository.findByTopicId(topicId);
    }

    public List<UserPermissionEntity> saveUserPermissions(List<UserPermissionEntity> userPermissions) {
        return userPermissionRepository.saveAll(userPermissions);
    }

    public UserPermissionEntity findPermissionByUserIdAndTopicId(Integer userId, Integer topicId) {
        return userPermissionRepository.findByUserIdAndTopicId(userId, topicId);
    }

}
