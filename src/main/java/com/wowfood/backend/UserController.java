package com.wowfood.backend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    // SIGN-UP API
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (repository.existsByPhone(user.getPhone())) {
            return ResponseEntity.badRequest().body("Phone number already registered!");
        }
        User savedUser = repository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    // LOGIN API
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginData) {
        // User email ya phone dono se login kar sakta hai
        String identifier = loginData.getEmail();
        String password = loginData.getPassword();

        User user = repository.findByEmailAndPassword(identifier, password);
        if (user == null) {
            user = repository.findByPhoneAndPassword(identifier, password); // Phone se try karo
        }

        if (user != null) {
            return ResponseEntity.ok(user); // Login Success
        } else {
            return ResponseEntity.badRequest().body("Invalid Email/Phone or Password");
        }
    }
    // Admin ke liye saare users ki list
    @GetMapping
    public org.springframework.http.ResponseEntity<?> getAllUsers() {
        return org.springframework.http.ResponseEntity.ok(repository.findAll());
    }
}