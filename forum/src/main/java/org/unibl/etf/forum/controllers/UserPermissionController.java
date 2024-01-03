package org.unibl.etf.forum.controllers;

import org.unibl.etf.forum.models.entities.UserPermissionEntity;
import org.unibl.etf.forum.models.entities.UserPermissionEntityPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.forum.services.UserPermissionService;

import java.util.List;

@RestController
@RequestMapping("/api/userPermissions")
public class UserPermissionController {

    @Autowired
    private UserPermissionService userPermissionService;

    @GetMapping("/")
    public List<UserPermissionEntity> getAllUserPermissions() {
        return userPermissionService.findAll();
    }

    @GetMapping("/{permissionId}/{userId}")
    public ResponseEntity<UserPermissionEntity> getUserPermissionById(@PathVariable Integer permissionId, @PathVariable Integer userId) {
        UserPermissionEntityPK id = new UserPermissionEntityPK(permissionId, userId);
        UserPermissionEntity userPermission = userPermissionService.findById(id);
        if (userPermission != null) {
            return ResponseEntity.ok(userPermission);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public UserPermissionEntity createUserPermission(@RequestBody UserPermissionEntity userPermission) {
        return userPermissionService.save(userPermission);
    }

    @DeleteMapping("/{permissionId}/{userId}")
    public ResponseEntity<?> deleteUserPermission(@PathVariable Integer permissionId, @PathVariable Integer userId) {
        UserPermissionEntityPK id = new UserPermissionEntityPK(permissionId, userId);
        if (userPermissionService.findById(id) != null) {
            userPermissionService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Add additional endpoints as needed
}
