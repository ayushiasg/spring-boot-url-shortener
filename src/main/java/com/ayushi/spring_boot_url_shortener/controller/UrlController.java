package com.ayushi.spring_boot_url_shortener.controller;

import com.ayushi.spring_boot_url_shortener.model.ShortUrl;
import com.ayushi.spring_boot_url_shortener.service.UrlShorteningService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
public class UrlController {

    private final UrlShorteningService service;

    public UrlController(UrlShorteningService service) {
        this.service = service;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortUrl> shorten(@RequestParam String url) {
        ShortUrl shortUrl = service.shortenUrl(url);
        return ResponseEntity.status(HttpStatus.CREATED).body(shortUrl);
    }

    @GetMapping("/{shortCode}")
    public void redirect(@PathVariable String shortCode, HttpServletResponse response)
            throws IOException {

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
}

