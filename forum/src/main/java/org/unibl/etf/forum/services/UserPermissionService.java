package org.unibl.etf.forum.services;

import org.unibl.etf.forum.models.entities.UserPermissionEntity;
import org.unibl.etf.forum.models.entities.UserPermissionEntityPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.forum.repositories.UserPermissionRepository;

import java.util.List;

@Service
public class UserPermissionService {

    @Autowired
    private UserPermissionRepository userPermissionRepository;

    public List<UserPermissionEntity> findAll() {
        return userPermissionRepository.findAll();
    }

    public UserPermissionEntity findById(UserPermissionEntityPK id) {
        return userPermissionRepository.findById(id).orElse(null);
    }

    public UserPermissionEntity save(UserPermissionEntity userPermission) {
        return userPermissionRepository.save(userPermission);
    }

    public void deleteById(UserPermissionEntityPK id) {
        userPermissionRepository.deleteById(id);
    }

    // Add more methods based on requirements
}
