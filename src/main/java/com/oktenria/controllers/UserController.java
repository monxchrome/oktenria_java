package com.oktenria.controllers;

import com.oktenria.entities.User;
import com.oktenria.services.UserService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{id}")
    public ResponseEntity<Optional<User>> getById(@PathVariable ObjectId id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteById(@PathVariable ObjectId id) {
        try {
            userService.deleteUser(id);

            return ResponseEntity.ok("Deleted");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error when deleting: " + e.getMessage());
        }
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<String> updateById(@PathVariable ObjectId id, @RequestBody User updatedUser) {
        try {
            userService.updateUser(id, updatedUser);

            return ResponseEntity.ok("Updated");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error when updating: " + e.getMessage());
        }
    }
}
