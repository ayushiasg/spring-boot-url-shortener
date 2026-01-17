package com.ayushi.spring_boot_url_shortener.dto;

import com.ayushi.spring_boot_url_shortener.model.ShortUrl;

import java.time.LocalDateTime;

public record ShortUrlResponse(
        String shortCode,
        String originalUrl,
        LocalDateTime expiresAt,
        LocalDateTime createdAt
) {
    public static ShortUrlResponse from(ShortUrl e) {
        return new ShortUrlResponse(
                e.getShortCode(),
                e.getOriginalUrl(),
                e.getExpiresAt(),
                e.getCreatedAt()
        );
    }
}

