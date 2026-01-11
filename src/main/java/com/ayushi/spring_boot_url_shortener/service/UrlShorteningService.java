package com.ayushi.spring_boot_url_shortener.service;

import com.ayushi.spring_boot_url_shortener.model.ShortUrl;
import com.ayushi.spring_boot_url_shortener.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UrlShorteningService {

    private final ShortUrlRepository repository;
    public UrlShorteningService(ShortUrlRepository repository) {
        this.repository = repository;
    }

    public ShortUrl shortenUrl(String originalUrl) {
        String shortCode = generateShortCode();
        ShortUrl shortUrl = new ShortUrl(shortCode, originalUrl);
        return repository.save(shortUrl);
    }

    public String getOriginalUrl(String shortCode) {
        return repository
                .findByShortCode(shortCode)
                .map(ShortUrl::getOriginalUrl)
                .orElse(null);
    }

    private String generateShortCode() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
