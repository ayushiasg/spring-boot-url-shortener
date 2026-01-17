package com.ayushi.spring_boot_url_shortener.controller;

import com.ayushi.spring_boot_url_shortener.dto.RegisterRequest;
import com.ayushi.spring_boot_url_shortener.model.User;
import com.ayushi.spring_boot_url_shortener.repository.UserRepository;
import com.ayushi.spring_boot_url_shortener.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User created = authService.register(user.getUsername(), user.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(created.getUsername());
    }


}
