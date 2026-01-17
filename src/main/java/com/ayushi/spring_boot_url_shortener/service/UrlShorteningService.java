    package com.ayushi.spring_boot_url_shortener.service;

    import com.ayushi.spring_boot_url_shortener.model.ShortUrl;
    import com.ayushi.spring_boot_url_shortener.model.User;
    import com.ayushi.spring_boot_url_shortener.repository.ShortUrlRepository;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Service;

    import java.time.LocalDateTime;
    import java.util.Map;
    import java.util.UUID;
    import java.util.concurrent.ConcurrentHashMap;

    @Service
    public class UrlShorteningService {

        private final ShortUrlRepository repository;
        private final AuthService authService;

        @Value("${app.default-expiry-in-days:30}")
        private int defaultExpiryInDays;

        public UrlShorteningService(ShortUrlRepository repository,
                                    AuthService authService) {
            this.repository = repository;
            this.authService = authService;
        }

        public ShortUrl shortenUrl(String originalUrl, User creator) {
            String shortCode = generateShortCode();
            LocalDateTime expiresAt = LocalDateTime.now().plusDays(defaultExpiryInDays);

            ShortUrl shortUrl = new ShortUrl(shortCode, originalUrl, expiresAt, creator);
            return repository.save(shortUrl);
        }



        public ShortUrl find(String shortCode) {
            return repository
                    .findByShortCode(shortCode)
                    .orElse(null);
        }

        private String generateShortCode() {
            return UUID.randomUUID().toString().substring(0, 8);
        }






    }
