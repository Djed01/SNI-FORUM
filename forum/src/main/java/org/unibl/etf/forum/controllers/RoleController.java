package org.unibl.etf.forum.controllers;

import org.unibl.etf.forum.models.entities.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.forum.services.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    public List<RoleEntity> getAllRoles() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleEntity> getRoleById(@PathVariable Integer id) {
        RoleEntity role = roleService.findById(id);
        if (role != null) {
            return ResponseEntity.ok(role);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public RoleEntity createRole(@RequestBody RoleEntity role) {
        return roleService.save(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleEntity> updateRole(@PathVariable Integer id, @RequestBody RoleEntity roleDetails) {
        RoleEntity role = roleService.findById(id);
        if (role != null) {
            // Update the properties of role with roleDetails
            return ResponseEntity.ok(roleService.save(role));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Integer id) {
        if (roleService.findById(id) != null) {
            roleService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Add additional endpoints as needed
}
