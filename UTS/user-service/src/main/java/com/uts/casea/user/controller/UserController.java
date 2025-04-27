package com.uts.casea.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.uts.casea.user.model.User;
import com.uts.casea.user.service.UserService;



@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    // Endpoint untuk mengambil semua user
    @GetMapping
    public List<User> getAllUsers() {
        log.info("GET /api/users accessed");
        return userService.getAll();
    }
    
    // Endpoint untuk mengambil user berdasarkan id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.info("GET /api/users/{} accessed", id);
            return userService.getById(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint membuat user baru
    @PostMapping
    public User  createUser(@RequestBody User user) {
        log.info("POST /api/users accessed with body: {}", user);
            return userService.createUser(user);
    }

    // Endpoint untuk update user yang sudah ada
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        log.info("PUT /api/users/{} accessed with body: {}", id, userDetails);

        try {
            User updateUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updateUser);
        } catch (RuntimeException e) {
            log.warn("PUT /api/users/{} failed: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint DELETE user
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
    log.info("DELETE /api/users/{} accessed", id);
    Map<String, String> response = new HashMap<>();
        try {
            userService.deleteUser(id);
            response.put("message", "User berhasil dihapus");
            return ResponseEntity.ok("User deleted successfully");
        } catch (RuntimeException e) {
            response.put("message", "User tidak duserukan dengan id" + id);
            log.warn("DELETE /api/users/{} failed: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
}
