package com.ayushi.spring_boot_url_shortener.repository;

import com.ayushi.spring_boot_url_shortener.model.ShortUrl;
import com.ayushi.spring_boot_url_shortener.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    Optional<ShortUrl> findByShortCode(String shortCode);

    List<ShortUrl> findByCreatedBy(User user);
    

}

