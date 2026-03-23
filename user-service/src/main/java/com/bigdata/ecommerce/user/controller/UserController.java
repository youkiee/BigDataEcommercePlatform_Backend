package com.bigdata.ecommerce.user.controller;

import com.bigdata.ecommerce.user.entity.User;
import com.bigdata.ecommerce.user.entity.UserBehavior;
import com.bigdata.ecommerce.user.service.UserBehaviorService;
import com.bigdata.ecommerce.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserBehaviorService userBehaviorService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PostMapping("/behavior")
    public ResponseEntity<?> collectBehavior(@RequestBody UserBehavior behavior) {
        userBehaviorService.collectBehavior(behavior);
        return ResponseEntity.ok("Behavior collected successfully");
    }

    @GetMapping("/behavior/{userId}")
    public ResponseEntity<?> getUserBehaviors(@PathVariable Long userId) {
        List<UserBehavior> behaviors = userBehaviorService.getUserBehaviors(userId);
        return ResponseEntity.ok(behaviors);
    }
}
