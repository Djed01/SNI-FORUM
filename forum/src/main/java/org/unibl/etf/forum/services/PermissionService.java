package org.unibl.etf.forum.services;

import org.unibl.etf.forum.models.entities.PermissionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.forum.repositories.PermissionRepository;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public List<PermissionEntity> findAll() {
        return permissionRepository.findAll();
    }

    public PermissionEntity findById(Integer id) {
        return permissionRepository.findById(id).orElse(null);
    }

    public PermissionEntity save(PermissionEntity permission) {
        return permissionRepository.save(permission);
    }

    public void deleteById(Integer id) {
        permissionRepository.deleteById(id);
    }

    // Add more methods based on requirements
}
