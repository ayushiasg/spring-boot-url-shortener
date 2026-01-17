package com.ayushi.spring_boot_url_shortener.controller;

import com.ayushi.spring_boot_url_shortener.dto.ShortUrlResponse;
import com.ayushi.spring_boot_url_shortener.model.ShortUrl;
import com.ayushi.spring_boot_url_shortener.model.User;
import com.ayushi.spring_boot_url_shortener.repository.ShortUrlRepository;
import com.ayushi.spring_boot_url_shortener.service.AuthService;
import com.ayushi.spring_boot_url_shortener.service.UrlShorteningService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class UrlController {

    private final UrlShorteningService service;
    private final AuthService authService;
    private final ShortUrlRepository repository;

    public UrlController(UrlShorteningService service,
                         AuthService authService,
                         ShortUrlRepository repository) {
        this.service = service;
        this.authService = authService;
        this.repository = repository;
    }

    @PostMapping("/shorten")
    public ResponseEntity<?> shorten(@RequestParam String url) {
        User current = authService.currentUser();
        ShortUrl shortUrl = service.shortenUrl(url, current);
        return ResponseEntity.status(HttpStatus.CREATED).body(shortUrl);
    }

    @GetMapping("/{shortCode}")
    public void redirect(@PathVariable String shortCode,
                         HttpServletResponse response) throws IOException {

        ShortUrl shortUrl = service.find(shortCode);

        if (shortUrl == null) {
            response.sendError(HttpStatus.NOT_FOUND.value(), "Short URL not found");
            return;
        }

        if (shortUrl.getExpiresAt() != null &&
                shortUrl.getExpiresAt().isBefore(LocalDateTime.now())) {
            response.sendError(HttpStatus.GONE.value(), "Short URL expired");
            return;
        }

        response.sendRedirect(shortUrl.getOriginalUrl());
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication auth) {
        if (auth == null) return ResponseEntity.status(401).build();
        return ResponseEntity.ok(auth.getName());
    }

    @GetMapping("/my/urls")
    public ResponseEntity<?> myUrls() {
        User current = authService.currentUser();
        List<ShortUrl> urls = repository.findByCreatedBy(current);

        List<ShortUrlResponse> result = urls.stream()
                .map(ShortUrlResponse::from)
                .toList();

        return ResponseEntity.ok(result);
    }
}
