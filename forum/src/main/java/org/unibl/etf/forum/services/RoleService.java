package org.unibl.etf.forum.services;

import org.unibl.etf.forum.models.entities.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.unibl.etf.forum.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

    public RoleEntity findById(Integer id) {
        return roleRepository.findById(id).orElse(null);
    }

    public RoleEntity save(RoleEntity role) {
        return roleRepository.save(role);
    }

    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }

}