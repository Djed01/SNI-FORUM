package org.unibl.etf.forum.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.forum.models.entities.UserPermissionEntity;
import org.unibl.etf.forum.services.UserPermissionService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/userPermissions")
public class UserPermissionController {

    private final UserPermissionService userPermissionService;

    @Autowired
    public UserPermissionController(UserPermissionService userPermissionService) {
        this.userPermissionService = userPermissionService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserPermissionEntity>> getPermissionsByUserId(@PathVariable Integer userId) {
        return ResponseEntity.ok(userPermissionService.findByUserId(userId));
    }

    @GetMapping("/topic/{topicId}")
    public ResponseEntity<List<UserPermissionEntity>> getPermissionsByTopicId(@PathVariable Integer topicId) {
        return ResponseEntity.ok(userPermissionService.findByTopicId(topicId));
    }

//    @GetMapping("/username/{username}")
//    public ResponseEntity<List<UserPermissionEntity>> getPermissionsByUsername(@PathVariable String username) {
//        return ResponseEntity.ok(userPermissionService.findByUsername(username));
//    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/")
    public ResponseEntity<List<UserPermissionEntity>> addUserPermissions(@RequestBody List<UserPermissionEntity> userPermissions) {
        List<UserPermissionEntity> savedUserPermissions = userPermissionService.saveUserPermissions(userPermissions);
        return ResponseEntity.ok(savedUserPermissions);
    }


}