package org.unibl.etf.forum.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.unibl.etf.forum.models.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.forum.services.UserService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Integer id) {
        return userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDetails> getUserByUsername(@PathVariable String username) {
        UserDetails user = userService.loadUserByUsername(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Integer id, @RequestBody UserEntity user) {
        if (!userService.findUserById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        user.setId(id);
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        if (!userService.findUserById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/false")
    public ResponseEntity<List<UserEntity>> getAllInactiveUsers() {
        List<UserEntity> inactiveUsers = userService.findAllInactiveUsers();
        return ResponseEntity.ok(inactiveUsers);
    }

    // In UserController.java
    @CrossOrigin(origins = "http://localhost:4200")
    @PatchMapping("/{id}/activate")
    public ResponseEntity<UserEntity> activateUser(@PathVariable Integer id) {
        return userService.findUserById(id)
                .map(user -> {
                    user.setStatus(true);
                    userService.saveUser(user);
                    return ResponseEntity.ok(user);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/setRole/{userId}/{role}")
    public ResponseEntity<UserEntity> setUserRole(@PathVariable Integer userId, @PathVariable String role) {
        UserEntity user = userService.findUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));
        user.setRole(role);
        UserEntity updatedUser = userService.saveUser(user);
        return ResponseEntity.ok(updatedUser);
    }

}