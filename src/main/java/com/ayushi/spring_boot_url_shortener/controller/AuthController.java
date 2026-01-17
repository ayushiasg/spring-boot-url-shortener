package com.ayushi.spring_boot_url_shortener.controller;

import com.ayushi.spring_boot_url_shortener.dto.RegisterRequest;
import com.ayushi.spring_boot_url_shortener.model.User;
import com.ayushi.spring_boot_url_shortener.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public AuthController(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userRepo.findByUsername(req.username()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username already exists");
        }

        User user = new User(req.username(), encoder.encode(req.password()));
        userRepo.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
