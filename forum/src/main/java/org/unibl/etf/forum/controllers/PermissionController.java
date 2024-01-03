package org.unibl.etf.forum.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.forum.models.entities.PermissionEntity;
import org.unibl.etf.forum.services.PermissionService;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/")
    public List<PermissionEntity> getAllPermissions() {
        return permissionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionEntity> getPermissionById(@PathVariable Integer id) {
        PermissionEntity permission = permissionService.findById(id);
        if (permission != null) {
            return ResponseEntity.ok(permission);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public PermissionEntity createPermission(@RequestBody PermissionEntity permission) {
        return permissionService.save(permission);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermissionEntity> updatePermission(@PathVariable Integer id, @RequestBody PermissionEntity permissionDetails) {
        PermissionEntity permission = permissionService.findById(id);
        if (permission != null) {
            // Update the properties of permission with permissionDetails
            return ResponseEntity.ok(permissionService.save(permission));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePermission(@PathVariable Integer id) {
        if (permissionService.findById(id) != null) {
            permissionService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}